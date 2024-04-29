package pl.edu.pja.trainmate.core.domain.user.querydsl;


import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import pl.edu.pja.trainmate.core.common.Gender;

@Builder
@NoArgsConstructor(access = PRIVATE, force = true)
@Value
@FieldNameConstants
public class MenteeProjection {

    String firstname;
    String lastname;
    LocalDate dateOfBirth;
    String phone;
    String email;
    Gender gender;
    Integer weight;
    Integer height;

    @QueryProjection
    public MenteeProjection(
        String firstname,
        String lastname,
        LocalDate dateOfBirth,
        String phone,
        String email,
        Gender gender,
        Integer weight,
        Integer height) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
    }
}
