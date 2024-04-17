package pl.edu.pja.trainmate.core.domain.workoutplan.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.exercise.dto.ExerciseCreateDto;

@RequiredArgsConstructor
@Service
public class ExerciseFacade {

    private final ExerciseService service;

    public ResultDto<Long> create(ExerciseCreateDto exerciseCreateDto) {
        return service.createExercise(exerciseCreateDto);
    }
}
