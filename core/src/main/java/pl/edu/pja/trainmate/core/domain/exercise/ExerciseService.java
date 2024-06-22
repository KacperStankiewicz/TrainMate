package pl.edu.pja.trainmate.core.domain.exercise;

import static pl.edu.pja.trainmate.core.common.error.ExerciseErrorCode.COULD_NOT_CREATE_EXERCISE;
import static pl.edu.pja.trainmate.core.common.error.ExerciseErrorCode.MUSCLE_INVOLVED_MUST_NOT_BE_NULL;
import static pl.edu.pja.trainmate.core.common.error.ExerciseErrorCode.NAME_MUST_NOT_BE_NULL;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseCreateDto;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseData;
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
        validateDto(dto);
        var entity = buildexerciseEntity(dto);

        return ResultDto.ofValueOrError(repository.save(entity).getId(), COULD_NOT_CREATE_EXERCISE);
    }

    public void update(ExerciseDto dto) {
        var exercise = repository.findExactlyOneById(dto.getId());
        exercise.validateVersion(dto.getVersion());
        validateDto(dto);

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

    private void validateDto(ExerciseData data) {
        if (data.getName() == null || data.getName().isEmpty()) {
            throw new CommonException(NAME_MUST_NOT_BE_NULL);
        }

        if (data.getMuscleInvolved() == null) {
            throw new CommonException(MUSCLE_INVOLVED_MUST_NOT_BE_NULL);
        }
    }
}
