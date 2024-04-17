package pl.edu.pja.trainmate.core.domain.workoutplan.exercise.mapper;

import lombok.NoArgsConstructor;
import pl.edu.pja.trainmate.core.domain.workoutplan.exercise.ExerciseEntity;
import pl.edu.pja.trainmate.core.domain.workoutplan.exercise.dto.ExerciseCreateDto;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ExerciseMapper {

    public static ExerciseEntity mapToExerciseEntity(ExerciseCreateDto createDto) {
        return ExerciseEntity.builder()
            .name(createDto.getName())
            .description(createDto.getDescription())
            .url(createDto.getUrl())
            .build();
    }
}
