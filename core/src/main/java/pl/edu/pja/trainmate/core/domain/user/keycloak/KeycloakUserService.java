package pl.edu.pja.trainmate.core.domain.user.keycloak;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.COULD_NOT_CREATE_MENTEE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.exception.CommonException;

@Slf4j
@Service
@RequiredArgsConstructor
class KeycloakUserService implements KeycloakService {

    private static final List<String> DEFAULT_REQUIRED_ACTIONS = List.of("VERIFY_EMAIL", "UPDATE_PASSWORD");

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak-properties.redirect-url}")
    private String redirectUrl;

    private final Keycloak keycloak;

    public UserRepresentation createUser(String email) {
        var resource = getUsersResource();

        var userRepresentation = prepareUserRepresentation(email);
        try (var response = resource.create(userRepresentation)) {

            if (!CREATED.equals(response.getStatusInfo().toEnum())) {
                throw new CommonException(COULD_NOT_CREATE_MENTEE);
            }
        }

        var user = getUserByEmail(email);
        resource.get(user.getId()).executeActionsEmail(clientId, redirectUrl, userRepresentation.getRequiredActions());
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
        var user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(email);
        user.setEmail(email);
        user.setEmailVerified(false);
        user.setRequiredActions(DEFAULT_REQUIRED_ACTIONS);

        return user;
    }

    private UserRepresentation getUserByEmail(String email) {
        return getUsersResource().searchByEmail(email, true).get(0);
    }
}
