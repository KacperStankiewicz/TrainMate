package pl.edu.pja.trainmate.core.domain.user.querydsl;


import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Builder
@NoArgsConstructor(access = PRIVATE, force = true)
@Value
@FieldNameConstants
public class UserProjection {

    String firstname;
    String lastname;
    LocalDate dateOfBirth;
    String phone;
    String email;

    @QueryProjection
    public UserProjection(String firstname, String lastname, LocalDate dateOfBirth, String phone, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
    }
}
