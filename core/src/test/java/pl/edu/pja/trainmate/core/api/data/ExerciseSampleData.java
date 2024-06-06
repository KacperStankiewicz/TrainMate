package pl.edu.pja.trainmate.core.api.data;

import static pl.edu.pja.trainmate.core.common.Muscle.MIDDLE_CHEST;
import static pl.edu.pja.trainmate.core.common.Muscle.MuscleGroup.CHEST;

import pl.edu.pja.trainmate.core.domain.exercise.ExerciseEntity;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseCreateDto;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseDto;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.ExerciseSearchCriteria;

public class ExerciseSampleData {

    public static ExerciseSearchCriteria.ExerciseSearchCriteriaBuilder getSearchCriteriaBuilder() {
        return ExerciseSearchCriteria.builder()
            .name("Bench Press")
            .muscle(MIDDLE_CHEST)
            .muscleGroup(CHEST);
    }

    public static ExerciseCreateDto.ExerciseCreateDtoBuilder getCreateDtoBuilder() {
        return ExerciseCreateDto.builder()
            .name("Bench Press")
            .description("description")
            .url("url")
            .muscleInvolved(MIDDLE_CHEST);
    }

    public static ExerciseDto.ExerciseDtoBuilder getExerciseDtoBuilder() {
        return ExerciseDto.builder()
            .id(1L)
            .name("Bench Press")
            .description("description")
            .url("url")
            .muscleInvolved(MIDDLE_CHEST);
    }

    public static ExerciseEntity.ExerciseEntityBuilder getExerciseEntityBuilder() {
        return ExerciseEntity.builder()
            .name("Bench Press")
            .description("description")
            .url("url")
            .muscleInvolved(MIDDLE_CHEST);
    }
}
