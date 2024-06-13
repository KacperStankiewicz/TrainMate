package pl.edu.pja.trainmate.core.domain.exercise;

import static pl.edu.pja.trainmate.core.common.error.ExerciseErrorCode.COULD_NOT_CREATE_EXERCISE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseCreateDto;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseDto;

@RequiredArgsConstructor
@Service
class ExerciseService {

    private final ExerciseRepository repository;

    public void deleteById(BasicAuditDto dto) {
        var entity = repository.findExactlyOneById(dto.getId());
        entity.validateVersion(dto.getVersion());
        repository.deleteById(dto.getId());
    }

    public ResultDto<Long> create(ExerciseCreateDto dto) {
        var entity = buildexerciseEntity(dto);

        return ResultDto.ofValueOrError(repository.save(entity).getId(), COULD_NOT_CREATE_EXERCISE);
    }

    public void update(ExerciseDto dto) {
        var exercise = repository.findExactlyOneById(dto.getId());
        exercise.validateVersion(dto.getVersion());

        exercise.update(dto);

        repository.saveAndFlush(exercise);
    }

    private ExerciseEntity buildexerciseEntity(ExerciseCreateDto dto) {
        return ExerciseEntity.builder()
            .name(dto.getName())
            .description(dto.getDescription())
            .url(dto.getUrl())
            .muscleInvolved(dto.getMuscleInvolved())
            .build();
    }
}
