package pl.edu.pja.trainmate.core.config.security;

import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.domain.user.PersonalInfo;

@FieldNameConstants
@Value
@Builder
@NoArgsConstructor(force = true, access = PRIVATE)
public class LoggedUserDataDto {

    UserId userId;
    RoleType role;
    PersonalInfo personalInfo;
    boolean firstLogin;

    @QueryProjection
    public LoggedUserDataDto(UserId userId, RoleType role, PersonalInfo personalInfo, boolean firstLogin) {
        this.userId = userId;
        this.role = role;
        this.personalInfo = personalInfo;
        this.firstLogin = firstLogin;
    }
}
