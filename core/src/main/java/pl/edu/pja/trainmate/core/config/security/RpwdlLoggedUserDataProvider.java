package pl.edu.pja.trainmate.core.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.domain.user.UserRepository;
import pl.edu.pja.trainmate.core.domain.user.querydsl.UserQueryService;

@Service
@RequiredArgsConstructor
public class RpwdlLoggedUserDataProvider implements LoggedUserDataProvider {

    private final UserIdProvider userIdProvider;
    private final UserQueryService queryService;
    private final UserRepository repository;

    @Override
    public LoggedUserDataDto getUserDetails() {
        return queryService.getUserByKeycloakId(userIdProvider.getLoggedUserId().getKeycloakId());
    }
}