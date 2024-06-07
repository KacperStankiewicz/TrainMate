package pl.edu.pja.trainmate.core;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.edu.pja.trainmate.core.config.emailsender.EmailSenderProperty;

@OpenAPIDefinition(
    info = @Info(
        title = "API provided by trainmate-core module",
        description = "Used for ...",
        version = "1.0"
    ),
    servers = @Server(url = "/api/tm-core"),
    security = @SecurityRequirement(name = "Authorization")
)
@SecurityScheme(
    type = HTTP,
    name = "Authorization",
    scheme = "bearer",
    bearerFormat = "JWT"
)
@EnableConfigurationProperties(EmailSenderProperty.class)
@EnableScheduling
@SpringBootApplication
@EnableJpaAuditing
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

}
