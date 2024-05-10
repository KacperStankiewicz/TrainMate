package pl.edu.pja.trainmate.core.domain.workoutplan.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WorkoutPlanDto {

    Long id;
    String name;
    String category;
}
