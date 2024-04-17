package pl.edu.pja.trainmate.core.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.exercise.ExerciseFacade;
import pl.edu.pja.trainmate.core.domain.workoutplan.exercise.dto.ExerciseCreateDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    private final ExerciseFacade exerciseFacade;

    @PostMapping("/create")
    public ResultDto<Long> createExercise(@RequestBody ExerciseCreateDto exerciseCreateDto) {
        return exerciseFacade.create(exerciseCreateDto);
    }
}
