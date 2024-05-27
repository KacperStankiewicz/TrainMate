package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.annotation.HasRole;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataProvider;
import pl.edu.pja.trainmate.core.domain.user.UserEntity;
import pl.edu.pja.trainmate.core.domain.user.UserRepository;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    @HasRole(roleType = {
        PERSONAL_TRAINER, TRAINED_PERSON
    })
    @PostMapping("/get-current-user-info")
    public UserEntity getLoggedUserInfo() {
        String keycloakId = loggedUserDataProvider.getUserDetails().getUserId().getKeycloakId();
        return userRepository.getUserByKeycloakId(keycloakId);
    }


    private final LoggedUserDataProvider loggedUserDataProvider;

    private final UserRepository userRepository;
}
