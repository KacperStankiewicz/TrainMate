package pl.edu.pja.trainmate.core.domain.training.dto;

import static lombok.AccessLevel.PRIVATE;

import java.time.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
@Value
public class TrainingUnitCreateDto {

    Long workoutPlanId;
    DayOfWeek dayOfWeek;
    Long weekNumber;
}
