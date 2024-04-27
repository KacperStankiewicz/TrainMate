package pl.edu.pja.trainmate.core.domain.training;

import static pl.edu.pja.trainmate.core.common.error.ExerciseItemErrorCode.COULD_NOT_CREATE_EXERCISE_ITEM;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseItemEntity;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseItemRepository;
import pl.edu.pja.trainmate.core.domain.exercise.Volume;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitDto;

@RequiredArgsConstructor
@Service
public class TrainingUnitService {

    private final TrainingUnitRepository trainingUnitRepository;
    private final ExerciseItemRepository exerciseItemRepository;

    public ResultDto<Long> create(TrainingUnitDto dto) {
        Long trainingUnitid = dto.getId();
        if (Objects.nonNull(trainingUnitid)) {
            trainingUnitid = buildTrainingUnitEntityAndSave(dto).getId();
        }
        var buildExerciseItemEntity = buildExerciseItemEntity(dto, trainingUnitid);
        var exerciseItemEntity = exerciseItemRepository.save(buildExerciseItemEntity);
        return ResultDto.ofValueOrError(exerciseItemEntity.getId(), COULD_NOT_CREATE_EXERCISE_ITEM);
    }

    public void deleteTrainingUnit(Long trainingUnitId) {
        trainingUnitRepository.deleteById(trainingUnitId);
        exerciseItemRepository.deleteByTrainingUnitId(trainingUnitId);
    }

    public void deleteExerciseItem(Long exerciseItemId) {
        exerciseItemRepository.deleteById(exerciseItemId);
    }

    public void updateTrainingUnit(TrainingUnitDto dto) {
        var trainingUnit = trainingUnitRepository.findExactlyOneById(dto.getId());
        trainingUnit.update(dto);
        trainingUnitRepository.saveAndFlush(trainingUnit);
    }

    public void updateExerciseItem(TrainingUnitDto dto) {
        var exerciseItem = exerciseItemRepository.findExactlyOneById(dto.getId());
        exerciseItem.update(dto);
        exerciseItemRepository.saveAndFlush(exerciseItem);
    }

    private TrainingUnitEntity buildTrainingUnitEntityAndSave(TrainingUnitDto dto) {
        return trainingUnitRepository.save(TrainingUnitEntity.builder()
            .workoutPlanId(dto.getWorkoutPlanId())
            .dayOfWeek(dto.getDayOfWeek())
            .weekNumber(dto.getWeekNumber())
            .build());
    }

    private ExerciseItemEntity buildExerciseItemEntity(TrainingUnitDto dto, Long trainingUnitId) {
        return ExerciseItemEntity.builder()
            .exerciseId(dto.getExerciseId())
            .trainingUnitId(trainingUnitId)
            .workoutPlanId(dto.getWorkoutPlanId())
            .volume(buildVolume(dto))
            .build();
    }

    private Volume buildVolume(TrainingUnitDto dto) {
        return Volume.builder()
            .repetitions(dto.getRepetitions())
            .tempo(dto.getTempo())
            .weight(dto.getWeight())
            .targetRir(dto.getTargetRir())
            .actualRir(dto.getActualRir())
            .sets(dto.getSets())
            .build();
    }
}
