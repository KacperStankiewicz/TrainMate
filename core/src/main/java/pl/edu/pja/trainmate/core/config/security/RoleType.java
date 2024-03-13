package pl.edu.pja.trainmate.core.config.security;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

@AllArgsConstructor
@Getter
@FieldNameConstants
public enum RoleType {

    TRAINED_PERSON("trained_person"),
    PERSONAL_TRAINER("personal_trainer"),
    ADMIN("admin");

    private final String value;

    private static final Map<String, RoleType> stringToEnumMap = new HashMap<>();

    static {
        for (RoleType role : RoleType.values()) {
            stringToEnumMap.put(role.getValue(), role);
        }
    }

    public static RoleType fromString(String text) {
        return stringToEnumMap.get(text.toLowerCase());
    }
}
