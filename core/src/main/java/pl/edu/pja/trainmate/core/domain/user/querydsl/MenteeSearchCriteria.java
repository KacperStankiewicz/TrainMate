package pl.edu.pja.trainmate.core.domain.user.querydsl;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.common.NumberRange;

@Value
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
@Builder
public class MenteeSearchCriteria {

    String firstname;
    String lastname;
    String email;
    NumberRange ageRange;
}

