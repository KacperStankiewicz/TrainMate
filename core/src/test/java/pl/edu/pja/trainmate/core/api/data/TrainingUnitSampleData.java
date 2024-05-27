package pl.edu.pja.trainmate.core.api.data;

import static java.time.DayOfWeek.MONDAY;

import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitDto;

public class TrainingUnitSampleData {

    public static TrainingUnitDto getSampleTrainingUnitDto() {
        return TrainingUnitDto.builder()
            .id(1L)
            .workoutPlanId(1L)
            .dayOfWeek(MONDAY)
            .weekNumber(1L)
            .exerciseId(1L)
            .repetitions(1)
            .tempo("1:1:1:1")
            .weight(1)
            .rir(1)
            .sets(1)
            .build();

    }
}
