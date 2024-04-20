package pl.edu.pja.trainmate.core.config.security;

import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import pl.edu.pja.trainmate.core.common.UserId;

@FieldNameConstants
@Value
@Builder
@NoArgsConstructor(force = true, access = PRIVATE)
public class LoggedUserDataDto {

    UserId userId;
    RoleType role;

    @QueryProjection
    public LoggedUserDataDto(UserId userId, RoleType role) {
        this.userId = userId;
        this.role = role;
    }
}
