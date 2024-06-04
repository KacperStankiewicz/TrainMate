package pl.edu.pja.trainmate.core.api.data;

import pl.edu.pja.trainmate.core.common.DateRange;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.domain.workoutplan.WorkoutPlanEntity;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanCreateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanDto;

public class WorkoutPlanSampleData {

    public static WorkoutPlanCreateDto.WorkoutPlanCreateDtoBuilder getSampleWorkoutPlanCreateDtoBuilder() {
        return WorkoutPlanCreateDto.builder()
            .name("name")
            .userId("userId")
            .category("category")
            .dateRange(DateRange.fromNow());
    }

    public static WorkoutPlanDto.WorkoutPlanDtoBuilder getSampleWorkoutPlanDtoBuilder() {
        return WorkoutPlanDto.builder()
            .name("name")
            .id(1L)
            .category("category");
    }

    public static WorkoutPlanEntity.WorkoutPlanEntityBuilder getSampleWorkoutPlanEntityBuilder() {
        return WorkoutPlanEntity.builder()
            .name("name")
            .userId(UserId.valueOf("userId"))
            .category("category")
            .dateRange(DateRange.fromNow());
    }
}
