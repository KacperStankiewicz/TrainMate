package pl.edu.pja.trainmate.core.config.security;

import io.vavr.control.Option;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.UserId;

@Service
class KeycloakUserIdProvider implements UserIdProvider {

    private static final String UNKNOWN_AUDITOR = "<unknown>";

    private static UserId getUserFromAuthentication(Authentication authentication) {
        var keycloakUserId = Option.of(authentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .toTry()
            .mapTry(KeycloakPrincipal.class::cast)
            .toOption()
            .map(KeycloakPrincipal::getKeycloakSecurityContext)
            .map(KeycloakSecurityContext::getToken)
            .map(AccessToken::getSubject)
            .getOrElse(UNKNOWN_AUDITOR);

        return UserId.valueOf(keycloakUserId);
    }

    @Override
    public UserId getLoggedUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return getUserFromAuthentication(authentication);
    }
}