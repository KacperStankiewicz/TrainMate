package pl.edu.pja.trainmate.core.domain.user.dto;

import java.time.LocalDate;
import lombok.Value;


@Value
public class MenteeCreateDto {

    String firstname;
    String lastname;
    LocalDate dateOfBirth;
    String phone;
    String email;
}
