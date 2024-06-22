package pl.edu.pja.trainmate.core.config.jpa;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.config.security.UserIdProvider;


@RequiredArgsConstructor
public class DefaultAuditorAware implements AuditorAware<UserId> {

    private final UserIdProvider userIdProvider;

    @Override
    public Optional<UserId> getCurrentAuditor() {
        return Optional.of(userIdProvider.getLoggedUserId());
    }
}
