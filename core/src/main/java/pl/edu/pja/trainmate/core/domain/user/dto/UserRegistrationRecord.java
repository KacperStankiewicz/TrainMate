package pl.edu.pja.trainmate.core.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegistrationRecord {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}