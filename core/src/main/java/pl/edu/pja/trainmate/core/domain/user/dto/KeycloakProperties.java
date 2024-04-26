package pl.edu.pja.trainmate.core.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeycloakProperties {
    public final String authServerUrl = "http://localhost:8123/realms/trainmate";
    public final String realm = "trainmate";
    public final String adminClientId = "train-mate";
    public final String adminClientSecret = "FZhgNU0wONm4ZAB4j1D7811raPTTWGPH";
}
