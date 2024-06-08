package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.email.TemplateType.WEEKLY_REPORT_NOTIFICATION;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.email.EmailService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @GetMapping
    public void sendTest() {
        emailService.sendEmail("kacperstankiewicz506@gmail.com", WEEKLY_REPORT_NOTIFICATION);
    }
}
