package pl.edu.pja.trainmate.core.domain.user.dto;

import java.time.LocalDate;
import lombok.Value;
import pl.edu.pja.trainmate.core.common.Gender;

@Value
public class MenteeUpdateDto {

    String email;
    String firstname;
    String lastname;
    LocalDate dateOfBirth;
    String phone;
    Gender gender;
    Integer height;
}
