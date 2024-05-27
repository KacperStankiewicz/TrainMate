package pl.edu.pja.trainmate.core.api.data;

import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanCreateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanDto;

public class WorkoutPlanSampleData {

    public static WorkoutPlanCreateDto getWorkoutPlanCreateDto() {
        return WorkoutPlanCreateDto.builder()
            .name("name")
            .userId("userId")
            .category("category")
            .build();
    }

    public static WorkoutPlanDto getWorkoutPlanDto() {
        return WorkoutPlanDto.builder()
            .name("name")
            .id(1L)
            .category("category")
            .build();
    }
}
