package pl.edu.pja.trainmate.core.domain.exercise;

import static pl.edu.pja.trainmate.core.common.error.ExerciseErrorCode.COULD_NOT_CREATE_EXERCISE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseCreateDto;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseDto;

@RequiredArgsConstructor
@Service
class ExerciseService {

    private final ExerciseRepository repository;

    public void deleteById(Long exerciseId) {
        repository.deleteById(exerciseId);
    }

    public ResultDto<Long> create(ExerciseCreateDto dto) {
        var entity = buildexerciseEntity(dto);

        return ResultDto.ofValueOrError(repository.save(entity).getId(), COULD_NOT_CREATE_EXERCISE);
    }

    private ExerciseEntity buildexerciseEntity(ExerciseCreateDto dto) {
        return ExerciseEntity.builder()
            .name(dto.getName())
            .description(dto.getDescription())
            .url(dto.getUrl())
            .muscleInvolved(dto.getMuscleInvolved())
            .build();
    }

    public void update(ExerciseDto dto) {
        var exercise = repository.findExactlyOneById(dto.getId());

        exercise.update(dto);

        repository.saveAndFlush(exercise);
    }
}
