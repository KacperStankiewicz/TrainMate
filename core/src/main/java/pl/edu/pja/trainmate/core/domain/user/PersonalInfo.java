package pl.edu.pja.trainmate.core.domain.user;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edu.pja.trainmate.core.common.Gender;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
@Getter
@Builder
public class PersonalInfo {

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Enumerated(STRING)
    private Gender gender;
}
