package pl.edu.pja.trainmate.core.domain.training;

import static pl.edu.pja.trainmate.core.common.error.TrainingUnitErrorCode.COULD_NOT_CREATE_TRAINING_UNIT;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitCreateDto;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitDto;

@RequiredArgsConstructor
@Service
public class TrainingUnitService {

    private final TrainingUnitRepository repository;

    public ResultDto<Long> create(TrainingUnitCreateDto dto) {
        var entity = buildTrainingUnitEntity(dto);

        return ResultDto.ofValueOrError(repository.save(entity).getId(), COULD_NOT_CREATE_TRAINING_UNIT);
    }

    public void update(TrainingUnitDto dto) {
        var trainingUnit = repository.findExactlyOneById(dto.getId());

        trainingUnit.update(dto);

        repository.saveAndFlush(trainingUnit);
    }

    private TrainingUnitEntity buildTrainingUnitEntity(TrainingUnitCreateDto dto) {
        return TrainingUnitEntity.builder()
            .workoutPlanId(dto.getWorkoutPlanId())
            .dayOfWeek(dto.getDayOfWeek())
            .weekNumber(dto.getWeekNumber())
            .build();
    }
}
