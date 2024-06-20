package pl.edu.pja.trainmate.core.domain.training;

import static pl.edu.pja.trainmate.core.common.error.ExerciseItemErrorCode.COULD_NOT_CREATE_EXERCISE_ITEM;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.utils.WeekNumberCalculator;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseItemEntity;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseItemRepository;
import pl.edu.pja.trainmate.core.domain.exercise.Volume;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseItemCreateDto;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseItemUpdateDto;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseReportDto;
import pl.edu.pja.trainmate.core.domain.report.dto.ReportCreateDto;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitDto;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitUpdateDto;
import pl.edu.pja.trainmate.core.domain.training.querydsl.TrainingUnitProjection;
import pl.edu.pja.trainmate.core.domain.training.querydsl.TrainingUnitQueryService;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanProjection;

@RequiredArgsConstructor
@Service
class TrainingUnitService {

    private final TrainingUnitRepository trainingUnitRepository;
    private final ExerciseItemRepository exerciseItemRepository;
    private final TrainingUnitQueryService queryService;
    private final WeekNumberCalculator weekNumberCalculator;

    public List<TrainingUnitProjection> getTrainingUnitsForCurrentWeekForLoggedUser(WorkoutPlanProjection workoutPlan) {
        var weekNumber = weekNumberCalculator.calculate(workoutPlan.getDateRange().getFrom());

        return queryService.getTrainingUnitsByWorkoutPlanIdAndWeekNumber(workoutPlan.getId(), weekNumber);
    }

    public List<TrainingUnitProjection> getTrainingUnitsByWorkoutPlanIdAndWeek(Long workoutPlanId, Long week) {
        return queryService.getTrainingUnitsByWorkoutPlanIdAndWeekNumber(workoutPlanId, week);
    }

    public ExerciseReportDto getExerciseReportById(Long exerciseItemId) {
        var exerciseItem = exerciseItemRepository.findExactlyOneById(exerciseItemId);

        return ExerciseReportDto.builder()
            .version(exerciseItem.getVersion())
            .reportedSets(exerciseItem.getExerciseReport().getReportedSets())
            .remarks(exerciseItem.getExerciseReport().getRemarks())
            .build();
    }

    public ResultDto<Long> create(TrainingUnitDto dto) {
        Long trainingUnitId = dto.getId();
        if (trainingUnitId == null) {
            trainingUnitId = buildTrainingUnitEntityAndSave(dto).getId();
        }
        buildAndSaveExerciseItemEntity(dto.getWorkoutPlanId(), dto.getExerciseCreateDto(), trainingUnitId);
        return ResultDto.ofValueOrError(trainingUnitId, COULD_NOT_CREATE_EXERCISE_ITEM);
    }

    public void deleteTrainingUnit(BasicAuditDto dto) {
        var entity = trainingUnitRepository.findExactlyOneById(dto.getId());
        entity.validateVersion(dto.getVersion());
        trainingUnitRepository.delete(entity);
        exerciseItemRepository.deleteByTrainingUnitId(dto.getId());
    }

    public void deleteExerciseItem(BasicAuditDto dto) {
        var entity = exerciseItemRepository.findExactlyOneById(dto.getId());
        entity.validateVersion(dto.getVersion());
        exerciseItemRepository.delete(entity);
    }

    public void addExerciseToTrainingUnit(TrainingUnitUpdateDto dto) {
        var trainingUnit = trainingUnitRepository.findExactlyOneById(dto.getId());
        trainingUnit.validateVersion(dto.getVersion());
        trainingUnit.update(dto);
        trainingUnitRepository.saveAndFlush(trainingUnit);

        buildAndSaveExerciseItemEntity(trainingUnit.getWorkoutPlanId(), dto.getExerciseCreateDto(), trainingUnit.getId());
    }

    public void updateExerciseItem(ExerciseItemUpdateDto dto) {
        var exerciseItem = exerciseItemRepository.findExactlyOneById(dto.getId());
        exerciseItem.validateVersion(dto.getVersion());

        exerciseItem.update(dto);
        exerciseItemRepository.saveAndFlush(exerciseItem);
    }

    public void addExerciseItemReport(ReportCreateDto reportCreateDto) {
        var exerciseItem = getExerciseItemById(reportCreateDto.getExerciseItemId());
        exerciseItem.validateVersion(reportCreateDto.getVersion());

        exerciseItem.addReport(reportCreateDto);
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

    private ExerciseItemEntity buildAndSaveExerciseItemEntity(Long workoutPlanId, ExerciseItemCreateDto dto, Long trainingUnitId) {
        return exerciseItemRepository.save(ExerciseItemEntity.builder()
            .exerciseId(dto.getExerciseId())
            .trainingUnitId(trainingUnitId)
            .workoutPlanId(workoutPlanId)
            .volume(buildVolume(dto))
            .build());
    }

    private Volume buildVolume(ExerciseItemCreateDto dto) {
        return Volume.builder()
            .repetitions(dto.getRepetitions())
            .tempo(dto.getTempo())
            .weight(dto.getWeight())
            .rir(dto.getRir())
            .sets(dto.getSets())
            .build();
    }

    private ExerciseItemEntity getExerciseItemById(Long exerciseItemId) {
        return exerciseItemRepository.findExactlyOneById(exerciseItemId);
    }
}
