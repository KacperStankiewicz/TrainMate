package pl.edu.pja.trainmate.core.domain.training.dto;

import static lombok.AccessLevel.PRIVATE;

import java.time.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
public class TrainingUnitUpdateDto {

    Long id;
    DayOfWeek dayOfWeek;
    Long weekNumber;
}
