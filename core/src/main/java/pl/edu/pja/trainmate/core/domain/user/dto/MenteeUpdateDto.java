package pl.edu.pja.trainmate.core.domain.user.dto;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDate;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.common.Gender;

@Value
@NoArgsConstructor(access = PRIVATE, force = true)
public class MenteeUpdateDto {

    String email;
    String firstname;
    String lastname;
    LocalDate dateOfBirth;
    String phone;
    Gender gender;
    Integer height;
}
