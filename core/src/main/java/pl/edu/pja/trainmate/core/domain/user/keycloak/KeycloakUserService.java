package pl.edu.pja.trainmate.core.domain.user.keycloak;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static org.keycloak.representations.idm.CredentialRepresentation.PASSWORD;
import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.COULD_NOT_CREATE_MENTEE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.common.generator.RandomPasswordGenerator;

@Slf4j
@Service
@RequiredArgsConstructor
class KeycloakUserService implements KeycloakService {

    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;
    private final RandomPasswordGenerator passwordGenerator;

    public UserRepresentation createUser(String email) {
        var resource = getUsersResource();

        var userRepresentation = prepareUserRepresentation(email);
        try (var response = resource.create(userRepresentation)) {

            if (!CREATED.equals(response.getStatusInfo().toEnum())) {
                throw new CommonException(COULD_NOT_CREATE_MENTEE);
            }
        }

        var user = getUserByEmail(email);
        resource.get(user.getId()).executeActionsEmail(List.of("VERIFY_EMAIL", "UPDATE_PASSWORD"));
        return user;
    }

    public void updateUserEmail(String email, String keycloakId) {
        var user = getUsersResource().get(keycloakId);
        var representation = user.toRepresentation();

        representation.setEmail(email);
        user.update(representation);
    }

    public void enableOrDisableAccount(String keycloakId, boolean enabled) {
        var user = getUsersResource().get(keycloakId);
        var representation = user.toRepresentation();

        representation.setEnabled(enabled);
        user.update(representation);
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    private UserRepresentation prepareUserRepresentation(String email) {
        var credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(passwordGenerator.generate(10));
        credentialRepresentation.setTemporary(true);
        credentialRepresentation.setType(PASSWORD);

        var user = new UserRepresentation();
        user.setCredentials(List.of(credentialRepresentation));
        user.setEnabled(true);
        user.setUsername(email);
        user.setEmail(email);
        user.setEmailVerified(false);
        user.setRequiredActions(List.of("VERIFY_EMAIL", "UPDATE_PASSWORD", "UPDATE_PROFILE"));

        return user;
    }

    private UserRepresentation getUserByEmail(String email) {
        return getUsersResource().searchByEmail(email, true).get(0);
    }
}
