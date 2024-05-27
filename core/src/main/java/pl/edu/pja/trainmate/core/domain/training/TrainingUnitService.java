package pl.edu.pja.trainmate.core.domain.training;

import static pl.edu.pja.trainmate.core.common.error.ExerciseItemErrorCode.COULD_NOT_CREATE_EXERCISE_ITEM;
import static pl.edu.pja.trainmate.core.common.error.ReportErrorCode.EXERCISE_WAS_ALREADY_REPORTED;
import static pl.edu.pja.trainmate.core.common.error.ReportErrorCode.EXERCISE_WAS_NOT_REPORTED;

import io.vavr.control.Option;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseItemEntity;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseItemRepository;
import pl.edu.pja.trainmate.core.domain.exercise.Volume;
import pl.edu.pja.trainmate.core.domain.report.dto.ReportCreateDto;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitDto;

@RequiredArgsConstructor
@Service
class TrainingUnitService {

    private final TrainingUnitRepository trainingUnitRepository;
    private final ExerciseItemRepository exerciseItemRepository;

    public ResultDto<Long> create(TrainingUnitDto dto) {
        Long trainingUnitId = dto.getId();
        if (Objects.nonNull(trainingUnitId)) {
            trainingUnitId = buildTrainingUnitEntityAndSave(dto).getId();
        }
        var buildExerciseItemEntity = buildExerciseItemEntity(dto, trainingUnitId);
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
        var entity = TrainingUnitEntity.builder()
            .workoutPlanId(dto.getWorkoutPlanId())
            .dayOfWeek(dto.getDayOfWeek())
            .weekNumber(dto.getWeekNumber())
            .build();

        entity.calculateHash();

        return trainingUnitRepository.save(entity);
    }

    private ExerciseItemEntity buildExerciseItemEntity(TrainingUnitDto dto, Long trainingUnitId) {
        return exerciseItemRepository.save(ExerciseItemEntity.builder()
            .exerciseId(dto.getExerciseId())
            .trainingUnitId(trainingUnitId)
            .workoutPlanId(dto.getWorkoutPlanId())
            .volume(buildVolume(dto))
            .build());
    }

    private Volume buildVolume(TrainingUnitDto dto) {
        return Volume.builder()
            .repetitions(dto.getRepetitions())
            .tempo(dto.getTempo())
            .weight(dto.getWeight())
            .rir(dto.getRir())
            .sets(dto.getSets())
            .build();
    }

    public void addExerciseItemReport(ReportCreateDto reportCreateDto) {
        var exerciseItem = getExerciseItemById(reportCreateDto.getExerciseItemId());

        if (exerciseItem.isReported()) {
            throw new CommonException(EXERCISE_WAS_ALREADY_REPORTED);
        }

        exerciseItem.addReport(reportCreateDto);

        exerciseItemRepository.saveAndFlush(exerciseItem);
    }

    private ExerciseItemEntity getExerciseItemById(Long exerciseItemId) {
        return exerciseItemRepository.findExactlyOneById(exerciseItemId);
    }

    public void reviewReport(Long exerciseItemId) {
        var entity = Option.of(exerciseItemRepository.findExactlyOneById(exerciseItemId))
            .filter(ExerciseItemEntity::isReported)
            .peek(it -> it.getExerciseReport().markAsReviewed())
            .getOrElseThrow(() -> new CommonException(EXERCISE_WAS_NOT_REPORTED));

        exerciseItemRepository.save(entity);
    }
}