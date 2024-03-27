package pl.edu.pja.trainmate.core.common;

import org.springframework.stereotype.Service;

@Service
class KeycloakUserIdProvider implements UserIdProvider {

//    private static final String UNKNOWN_AUDITOR = "<unknown>";
//
//    private static UserId getUserFromAuthentication(Authentication authentication) {
//        var keycloakUserId = Option.of(authentication)
//            .filter(Authentication::isAuthenticated)
//            .map(Authentication::getPrincipal)
//            .toTry()
//            .mapTry(KeycloakPrincipal.class::cast)
//            .toOption()
//            .map(KeycloakPrincipal::getKeycloakSecurityContext)
//            .map(KeycloakSecurityContext::getToken)
//            .map(AccessToken::getSubject)
//            .getOrElse(UNKNOWN_AUDITOR);
//
//        return UserId.valueOf(keycloakUserId);
//    }
//
//    @Override
//    public UserId getLoggedUserId() {
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        return getUserFromAuthentication(authentication);
//    }

    @Override
    public UserId getLoggedUserId() {
        return getLoggedUserMOCKED();
    }

    private UserId getLoggedUserMOCKED() {
        return UserId.valueOf("123456-abcdef");
    }
}