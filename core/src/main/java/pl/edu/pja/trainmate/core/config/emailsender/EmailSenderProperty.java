package pl.edu.pja.trainmate.core.config.emailsender;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "email-sender")
public class EmailSenderProperty {

    private String host;
    private int port;
    private String username;
    private String password;
    private String protocol;
    private String from;
}
