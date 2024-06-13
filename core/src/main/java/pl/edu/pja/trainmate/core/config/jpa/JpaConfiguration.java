package pl.edu.pja.trainmate.core.config.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.config.security.UserIdProvider;


@Configuration
@EnableJpaAuditing(
    auditorAwareRef = "auditorAware",
    dateTimeProviderRef = "currentDateTimeProvider")
public class JpaConfiguration {

    @Bean
    public AuditorAware<UserId> auditorAware(UserIdProvider userIdProvider) {
        return new DefaultAuditorAware(userIdProvider);
    }

    @Bean
    public DateTimeProvider currentDateTimeProvider() {
        return new DefaultDateTimeProvider();
    }
}