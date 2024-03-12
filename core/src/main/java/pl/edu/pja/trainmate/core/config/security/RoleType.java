package pl.edu.pja.trainmate.core.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleType {
    TRAINED_PERSON("trained_person"),
    PERSONAL_TRAINER("personal_trainer"),
    ADMIN("admin");

    private final String value;
}
