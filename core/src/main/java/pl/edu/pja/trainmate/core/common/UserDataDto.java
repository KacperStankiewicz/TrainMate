package pl.edu.pja.trainmate.core.common;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import pl.edu.pja.trainmate.core.config.security.RoleType;

@FieldNameConstants
@Value
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class UserDataDto implements Serializable {

    UserId userId;
    RoleType role;
}
