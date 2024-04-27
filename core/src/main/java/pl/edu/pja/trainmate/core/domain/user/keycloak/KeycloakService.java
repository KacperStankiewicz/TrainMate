package pl.edu.pja.trainmate.core.domain.user.keycloak;

import java.util.List;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakService {

    List<UserRepresentation> searchUsers();

    UserRepresentation createUser(String email);
}
