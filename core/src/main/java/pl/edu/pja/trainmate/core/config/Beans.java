package pl.edu.pja.trainmate.core.config;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Value("${keycloak-admin.client-id}")
    private String clientId;

    @Value("${keycloak-admin.secret}")
    private String secret;

    @Value("${keycloak-admin.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Bean
    Keycloak keycloak() {
        return KeycloakBuilder.builder()
            .serverUrl(serverUrl)
            .realm(realm)
            .clientId(clientId)
            .clientSecret(secret)
            .grantType(CLIENT_CREDENTIALS)
            .build();
    }
}
