package pl.edu.pja.trainmate.core.domain.email;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.control.Try.of;
import static org.jvnet.fastinfoset.FastInfosetSerializer.UTF_8;
import static pl.edu.pja.trainmate.core.common.error.EmailErrorCode.CANNOT_CONNECT_TO_HOST;
import static pl.edu.pja.trainmate.core.common.error.EmailErrorCode.UNEXPECTED_ERROR_WHILE_SENDING_EMAIL;

import com.sun.mail.util.MailConnectException;
import io.vavr.CheckedConsumer;
import io.vavr.control.Try;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.exception.EmailException;
import pl.edu.pja.trainmate.core.config.emailsender.EmailSenderProperty;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private JavaMailSenderImpl sender;
    private final EmailSenderProperty emailSenderProperty;
    private final EmailTemplateRepository repository;

    public void sendEmail(String email, TemplateType type) {
        var template = repository.getEmailTemplateEntityByType(type);
        var params = new EmailParamsDto(email, template.getSubject(), template.getContent());
        log.debug("Sending email to :{}", email);
        Try.of(() -> params)
            .andThenTry(this::configureSender)
            .andThenTry(sender::testConnection)
            .mapTry(this::prepareMimeMessage)
            .andThenTry((CheckedConsumer<MimeMessage>) sender::send)
            .onFailure(this::mapFailure);
    }

    private void configureSender() {
        sender = new JavaMailSenderImpl();
        sender.setHost(emailSenderProperty.getHost());
        sender.setPort(emailSenderProperty.getPort());
        sender.setUsername(emailSenderProperty.getUsername());
        sender.setPassword(emailSenderProperty.getPassword());
        sender.setProtocol(emailSenderProperty.getProtocol());

        var props = sender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.trust", emailSenderProperty.getHost());
    }

    private MimeMessage prepareMimeMessage(EmailParamsDto params) {
        return of(() -> new MimeMessageHelper(sender.createMimeMessage(), true, UTF_8))
            .andThenTry(it -> fillInformationToMimeMessage(emailSenderProperty.getFrom(), params, it))
            .map(MimeMessageHelper::getMimeMessage)
            .onFailure(it -> log.error("Unsuccessfully prepared mime message, reason: {}", it.getMessage()))
            .get();
    }

    private void fillInformationToMimeMessage(String from, EmailParamsDto params, MimeMessageHelper messageHelper) {
        Try.run(() -> messageHelper.setFrom(from))
            .andThenTry(() -> messageHelper.setFrom(from))
            .andThenTry(() -> messageHelper.setTo(params.getTo()))
            .andThenTry(() -> messageHelper.setSubject(params.getSubject()))
            .andThenTry(() -> messageHelper.setText(params.getContent(), true));
    }

    private void mapFailure(Throwable thr) {
        Match(thr).of(
            Case($(instanceOf(MailConnectException.class)), it -> {
                throw new EmailException(CANNOT_CONNECT_TO_HOST, it.getHost());
            }),
            Case($(), it -> {
                throw new EmailException(UNEXPECTED_ERROR_WHILE_SENDING_EMAIL, it.getMessage());
            })
        );
    }
}
