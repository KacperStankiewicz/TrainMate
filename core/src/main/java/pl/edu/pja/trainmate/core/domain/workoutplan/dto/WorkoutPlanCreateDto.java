package pl.edu.pja.trainmate.core.domain.workoutplan.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
@Value
public class WorkoutPlanCreateDto {

    String name;
    String userId;
    String category;
}
