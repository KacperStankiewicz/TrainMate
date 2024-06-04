package pl.edu.pja.trainmate.core.domain.workoutplan.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
public class WorkoutPlanDto {

    Long id;
    String name;
    String category;
}
