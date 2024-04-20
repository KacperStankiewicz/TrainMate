package pl.edu.pja.trainmate.core.config.security;


import static pl.edu.pja.trainmate.core.common.UserId.UNKNOWN_AUDITOR;

import io.vavr.control.Option;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.edu.pja.trainmate.core.common.UserId;


public interface LoggedUserDataProvider {

    LoggedUserDataDto getUserDetails();

    default boolean isAnonymous() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
    }

    default UserId getLoggedUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return getUserIdFromAuthentication(authentication);
    }

    private UserId getUserIdFromAuthentication(Authentication authentication) {
        return Option.of(authentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .toTry()
            .mapTry(KeycloakPrincipal.class::cast)
            .toOption()
            .map(KeycloakPrincipal::getKeycloakSecurityContext)
            .map(KeycloakSecurityContext::getToken)
            .map(AccessToken::getSubject)
            .map(UserId::valueOf)
            .getOrElse(() -> UserId.valueOf(UNKNOWN_AUDITOR));
    }
}