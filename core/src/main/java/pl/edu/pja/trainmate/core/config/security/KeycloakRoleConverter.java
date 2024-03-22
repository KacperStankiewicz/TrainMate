package pl.edu.pja.trainmate.core.config.security;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class KeycloakRoleConverter implements Converter<Jwt, RoleType> {

    @Override
    public RoleType convert(Jwt jwt) {
        var realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        if (realmAccess == null || realmAccess.isEmpty()) {
            return null;
        }

        var roles = (List<String>) realmAccess.get("roles");

        return roles.stream()
            .map(RoleType::fromString)
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }
}