package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final LoggedUserDataProvider loggedUserDataProvider;

    private final UserRepository userRepository;

    @Operation(summary = "Get currently logged user info")
    @ApiResponse(
        responseCode = "200",
        description = "Got currently logged user info",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = {
        PERSONAL_TRAINER, TRAINED_PERSON
    })
    @GetMapping("/get-current-user-info")
    public UserEntity getLoggedUserInfo() {
        log.debug("Request to GET currently logged user info");
        String keycloakId = loggedUserDataProvider.getUserDetails().getUserId().getKeycloakId();
        var result = userRepository.getUserByKeycloakId(keycloakId);
        log.debug("Request to GET currently logged user info");
        return result;
    }
}
