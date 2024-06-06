package pl.edu.pja.trainmate.core.api.data;

import java.time.LocalDate;
import pl.edu.pja.trainmate.core.common.DateRange;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.domain.workoutplan.WorkoutPlanEntity;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanCreateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanUpdateDto;

public class WorkoutPlanSampleData {

    public static WorkoutPlanCreateDto.WorkoutPlanCreateDtoBuilder getSampleWorkoutPlanCreateDtoBuilder() {
        return WorkoutPlanCreateDto.builder()
            .name("name")
            .userId("userId")
            .category("category")
            .startDate(LocalDate.now())
            .durationInWeeks(1L);
    }

    public static WorkoutPlanUpdateDto.WorkoutPlanUpdateDtoBuilder getSampleWorkoutPlanDtoBuilder() {
        return WorkoutPlanUpdateDto.builder()
            .name("name")
            .id(1L)
            .category("category");
    }

    public static WorkoutPlanEntity.WorkoutPlanEntityBuilder getSampleActiveWorkoutPlanEntityBuilder() {
        return WorkoutPlanEntity.builder()
            .name("name")
            .userId(UserId.valueOf("userId"))
            .category("category")
            .dateRange(new DateRange(LocalDate.now(), LocalDate.now().plusWeeks(8)));
    }

    public static WorkoutPlanEntity.WorkoutPlanEntityBuilder getSampleInActiveWorkoutPlanEntityBuilder() {
        return WorkoutPlanEntity.builder()
            .name("name")
            .userId(UserId.valueOf("userId"))
            .category("category")
            .dateRange(new DateRange(LocalDate.now().plusDays(1), LocalDate.now().plusWeeks(8)));
    }
}
