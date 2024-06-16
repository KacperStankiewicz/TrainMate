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
public class TrainingUnitDto {

    Long id;
    Long workoutPlanId;
    DayOfWeek dayOfWeek;
    Long weekNumber;
    Long exerciseId;
    Integer repetitions;
    String tempo;
    Double weight;
    Integer rir;
    Integer sets;
}
