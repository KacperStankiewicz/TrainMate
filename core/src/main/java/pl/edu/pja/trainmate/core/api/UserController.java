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
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataDto;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataProvider;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final LoggedUserDataProvider loggedUserDataProvider;

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
    public LoggedUserDataDto getLoggedUserInfo() {
        log.debug("Request to GET currently logged user info");
        var result = loggedUserDataProvider.getUserDetails();
        log.debug("Request to GET currently logged user info");
        return result;
    }
}
