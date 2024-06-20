package pl.edu.pja.trainmate.core.domain.workoutplan;

import static pl.edu.pja.trainmate.core.common.error.WorkoutPlanErrorCode.COULD_NOT_CREATE_WORKOUT_PLAN;
import static pl.edu.pja.trainmate.core.common.error.WorkoutPlanErrorCode.START_DATE_MUST_NOT_BE_BEFORE_TODAY;
import static pl.edu.pja.trainmate.core.common.error.WorkoutPlanErrorCode.WORKOUT_PLAN_MUST_HAVE_DURATION;
import static pl.edu.pja.trainmate.core.common.error.WorkoutPlanErrorCode.WORKOUT_PLAN_MUST_HAVE_START_DATE;
import static pl.edu.pja.trainmate.core.common.error.WorkoutPlanErrorCode.WORKOUT_PLAN_START_DATE_OVERLAPS_WITH_OTHER_WORKOUT_PLAN;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.DateRange;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseItemRepository;
import pl.edu.pja.trainmate.core.domain.training.TrainingUnitRepository;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.AllWorkoutData;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanCreateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanUpdateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanListItemProjection;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanProjection;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanQueryService;

@RequiredArgsConstructor
@Service
class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final ExerciseItemRepository exerciseItemRepository;
    private final TrainingUnitRepository trainingUnitRepository;
    private final WorkoutPlanQueryService queryService;

    public AllWorkoutData getWorkoutPlanData(Long id) {
        return queryService.getWorkoutPlanData(id);
    }

    public List<WorkoutPlanListItemProjection> getAllWorkouPlansByUserId(UserId userId) {
        return queryService.getAllWorkoutPlansByUserId(userId);
    }

    public WorkoutPlanProjection getWorkoutPlanHeader(Long workoutPlanId) {
        return queryService.getWorkoutPlanHeader(workoutPlanId);
    }

    public ResultDto<Long> create(WorkoutPlanCreateDto workoutPlanCreateDto) {
        validateDto(workoutPlanCreateDto);
        checkForOverlappingWorkoutPlan(workoutPlanCreateDto.getStartDate(), UserId.valueOf(workoutPlanCreateDto.getUserId()));

        var entity = buildWorkoutPlanEntity(workoutPlanCreateDto);
        return ResultDto.ofValueOrError(workoutPlanRepository.save(entity).getId(), COULD_NOT_CREATE_WORKOUT_PLAN);
    }

    public void update(WorkoutPlanUpdateDto workoutPlanUpdateDto) {
        var workoutPlan = workoutPlanRepository.findExactlyOneById(workoutPlanUpdateDto.getId());
        workoutPlan.validateVersion(workoutPlanUpdateDto.getVersion());
        workoutPlan.checkIfModificationAllowed();
        validateDto(workoutPlanUpdateDto);
        if (workoutPlanUpdateDto.getStartDate() != workoutPlan.getDateRange().getFrom()) {
            checkForOverlappingWorkoutPlan(workoutPlanUpdateDto.getId(), workoutPlanUpdateDto.getStartDate(), workoutPlan.getUserId());
        }

        workoutPlan.update(workoutPlanUpdateDto);
        workoutPlanRepository.saveAndFlush(workoutPlan);
    }

    public void delete(BasicAuditDto dto) {
        var workoutPlan = workoutPlanRepository.findExactlyOneById(dto.getId());
        workoutPlan.validateVersion(dto.getVersion());
        workoutPlan.checkIfModificationAllowed();

        workoutPlanRepository.delete(workoutPlan);
        exerciseItemRepository.deleteByWorkoutPlanId(dto.getId());
        trainingUnitRepository.deleteByWorkoutPlanId(dto.getId());
    }

    public WorkoutPlanProjection getCurrentWorkoutPlanProjection(UserId userId) {
        return queryService.getCurrentWorkoutPlan(userId);
    }

    private void validateDto(WorkoutPlanDto dto) {
        if (dto.getDurationInWeeks() == null) {
            throw new CommonException(WORKOUT_PLAN_MUST_HAVE_DURATION);
        }

        if (dto.getStartDate() == null) {
            throw new CommonException(WORKOUT_PLAN_MUST_HAVE_START_DATE);
        }

        if (LocalDate.now().isAfter(dto.getStartDate())) {
            throw new CommonException(START_DATE_MUST_NOT_BE_BEFORE_TODAY);
        }
    }

    private void checkForOverlappingWorkoutPlan(Long updatingWorkoutPlanId, LocalDate startDate, UserId userId) {
        var workouts = getAllWorkouPlansByUserId(userId)
            .stream()
            .filter(it -> !Objects.equals(it.getId(), updatingWorkoutPlanId))
            .collect(Collectors.toList());

        checkOverlapping(startDate, workouts);
    }

    private void checkForOverlappingWorkoutPlan(LocalDate startDate, UserId userId) {
        var workouts = getAllWorkouPlansByUserId(userId);

        checkOverlapping(startDate, workouts);
    }

    private static void checkOverlapping(LocalDate startDate, List<WorkoutPlanListItemProjection> workouts) {
        if (!workouts.isEmpty()) {
            var overlaps = workouts.stream()
                .map(WorkoutPlanListItemProjection::getDateRange)
                .anyMatch(it -> it.isDateWithinRange(startDate) || startDate.isEqual(it.getFrom()) || startDate.isEqual(it.getTo()));
            if (overlaps) {
                throw new CommonException(WORKOUT_PLAN_START_DATE_OVERLAPS_WITH_OTHER_WORKOUT_PLAN);
            }
        }
    }

    private WorkoutPlanEntity buildWorkoutPlanEntity(WorkoutPlanCreateDto workoutPlanCreateDto) {
        return WorkoutPlanEntity.builder()
            .name(workoutPlanCreateDto.getName())
            .userId(UserId.valueOf(workoutPlanCreateDto.getUserId()))
            .category(workoutPlanCreateDto.getCategory())
            .dateRange(
                new DateRange(workoutPlanCreateDto.getStartDate(), workoutPlanCreateDto.getStartDate().plusWeeks(workoutPlanCreateDto.getDurationInWeeks())))
            .build();
    }
}
