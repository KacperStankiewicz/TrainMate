package pl.edu.pja.trainmate.core.domain.user.dto;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDate;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(access = PRIVATE, force = true)
public class MenteeCreateDto {

    String firstname;
    String lastname;
    LocalDate dateOfBirth;
    String phone;
    String email;
}
