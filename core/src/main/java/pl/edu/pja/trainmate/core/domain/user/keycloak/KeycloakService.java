package pl.edu.pja.trainmate.core.domain.user.keycloak;

import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakService {

    UserRepresentation createUser(String email);

    void updateUserEmail(String email, String keycloakId);

    void enableOrDisableAccount(String keycloakId, boolean enabled);
}
