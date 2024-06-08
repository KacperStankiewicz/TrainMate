package pl.edu.pja.trainmate.core.domain.workoutplan.dto;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
public class WorkoutPlanUpdateDto implements WorkoutPlanDto {

    Long id;
    Long version;
    String name;
    String category;
    LocalDate startDate;
    Long durationInWeeks;
}
