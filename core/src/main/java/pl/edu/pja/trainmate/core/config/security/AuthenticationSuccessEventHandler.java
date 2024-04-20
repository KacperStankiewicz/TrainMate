package pl.edu.pja.trainmate.core.config.security;

import static java.time.Instant.ofEpochSecond;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;

import io.vavr.control.Option;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class AuthenticationSuccessEventHandler {

    private final HttpServletRequest request;

    @EventListener({AuthenticationSuccessEvent.class})
    public void processAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
        var keycloakAccount = ((SimpleKeycloakAccount) event.getAuthentication().getDetails());
        var keycloakId = keycloakAccount.getPrincipal().getName();
        var authTime = keycloakAccount.getKeycloakSecurityContext().getToken().getAuth_time();
        var loginDateTime = Option.of(authTime)
            .map(it -> ofInstant(ofEpochSecond(authTime), systemDefault()))
            .getOrNull();
        var remoteAddr = Option.of(request)
            .map(HttpServletRequest::getRemoteAddr)
            .getOrNull();

        log.debug("Successfully authenticated user with keycloakId: {}", keycloakId);
        log.debug("login time : {} \n remoteAddr: {}", loginDateTime, remoteAddr);

    }
}