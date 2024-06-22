package pl.edu.pja.trainmate.core.domain.workoutplan.dto;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
@Value
public class WorkoutPlanCreateDto implements WorkoutPlanDto {

    String name;
    String userId;
    String category;
    LocalDate startDate;
    Long durationInWeeks;
}
