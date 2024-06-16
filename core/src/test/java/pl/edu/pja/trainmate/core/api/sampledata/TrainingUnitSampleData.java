package pl.edu.pja.trainmate.core.api.sampledata;

import static java.time.DayOfWeek.MONDAY;

import pl.edu.pja.trainmate.core.domain.exercise.ExerciseItemEntity;
import pl.edu.pja.trainmate.core.domain.exercise.Volume;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseItemUpdateDto;
import pl.edu.pja.trainmate.core.domain.training.TrainingUnitEntity;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitDto;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitUpdateDto;

public class TrainingUnitSampleData {

    public static TrainingUnitDto.TrainingUnitDtoBuilder getSampleTrainingUnitDtoBuilder() {
        return TrainingUnitDto.builder()
            .id(1L)
            .workoutPlanId(1L)
            .dayOfWeek(MONDAY)
            .weekNumber(1L)
            .exerciseId(1L)
            .repetitions(1)
            .tempo("1:1:1:1")
            .weight(1.0)
            .rir(1)
            .sets(1);
    }

    public static TrainingUnitEntity getSampleTrainingUnitEntity() {
        var entity = TrainingUnitEntity.builder()
            .workoutPlanId(1L)
            .dayOfWeek(MONDAY)
            .weekNumber(1L)
            .build();

        entity.calculateHash();
        return entity;
    }

    public static TrainingUnitEntity.TrainingUnitEntityBuilder getSampleTrainingUnitEntityBuilder() {
        var entity = TrainingUnitEntity.builder()
            .workoutPlanId(1L)
            .dayOfWeek(MONDAY)
            .weekNumber(1L)
            .build();

        entity.calculateHash();
        return entity.toBuilder();
    }

    public static TrainingUnitUpdateDto.TrainingUnitUpdateDtoBuilder getSampleTrainingUnitUpdateDtoBuilder() {
        return TrainingUnitUpdateDto.builder()
            .id(1L)
            .dayOfWeek(MONDAY)
            .weekNumber(1L);
    }

    public static ExerciseItemUpdateDto.ExerciseItemUpdateDtoBuilder getSampleExerciseItemUpdateDtoBuilder() {
        return ExerciseItemUpdateDto.builder()
            .id(1L)
            .trainingUnitId(1L)
            .exerciseId(2L)
            .repetitions(2)
            .tempo("2:2:2:2")
            .weight(2.0)
            .rir(2)
            .sets(2);
    }

    public static ExerciseItemEntity.ExerciseItemEntityBuilder getSampleExerciseItemEntityBuilder() {
        return ExerciseItemEntity.builder()
            .exerciseId(1L)
            .trainingUnitId(1L)
            .workoutPlanId(1L)
            .volume(Volume.builder()
                .repetitions(1)
                .tempo("1:1:1:1")
                .weight(1.0)
                .rir(1)
                .sets(1)
                .build());

    }

}
