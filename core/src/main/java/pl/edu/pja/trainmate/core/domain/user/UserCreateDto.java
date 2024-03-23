package pl.edu.pja.trainmate.core.domain.user;

import java.time.LocalDate;
import lombok.Value;


@Value
public class UserCreateDto {

    String firstname;
    String lastname;
    LocalDate dateOfBirth;
    String phone;
    String email;
}
