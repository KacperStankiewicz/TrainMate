package pl.edu.pja.trainmate.core.api;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.pja.trainmate.core.domain.user.KeycloakUserService;
import pl.edu.pja.trainmate.core.domain.user.dto.UserRegistrationRecord;

import java.security.Principal;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class KeycloakUserApi {


    private final KeycloakUserService keycloakUserService;

    @PostMapping
    public UserRegistrationRecord createUser(@RequestBody UserRegistrationRecord userRegistrationRecord) {

        return keycloakUserService.createUser(userRegistrationRecord);
    }

    @GetMapping
    public UserRepresentation getUser(Principal principal) {

        return keycloakUserService.getUserById(principal.getName());
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable String userId) {
        keycloakUserService.deleteUserById(userId);
    }
    @PutMapping("/{userId}/send-verify-email")
    public void sendVerificationEmail(@PathVariable String userId) {
        keycloakUserService.emailVerification(userId);
    }
    @PutMapping("/update-password")
    public void updatePassword(Principal principal) {
        keycloakUserService.updatePassword(principal.getName());
    }
}

