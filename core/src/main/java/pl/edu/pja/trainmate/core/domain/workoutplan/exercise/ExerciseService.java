package pl.edu.pja.trainmate.core.domain.workoutplan.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.UserIdProvider;
import pl.edu.pja.trainmate.core.domain.workoutplan.exercise.dto.ExerciseCreateDto;

import static pl.edu.pja.trainmate.core.domain.workoutplan.exercise.mapper.ExerciseMapper.mapToExerciseEntity;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final UserIdProvider userIdProvider;
    private final ExerciseRepository exerciseRepository;

    public ResultDto<Long> createExercise(ExerciseCreateDto exerciseCreateDto) {
        return ResultDto.ofValueOrError(exerciseRepository.save(mapToExerciseEntity(exerciseCreateDto)).getId(), null);
    }
}
