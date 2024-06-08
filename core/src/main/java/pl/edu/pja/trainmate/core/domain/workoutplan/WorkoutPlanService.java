package pl.edu.pja.trainmate.core.domain.workoutplan;

import static pl.edu.pja.trainmate.core.common.error.WorkoutPlanErrorCode.COULD_NOT_CREATE_WORKOUT_PLAN;
import static pl.edu.pja.trainmate.core.common.error.WorkoutPlanErrorCode.START_DATE_MUST_NOT_BE_BEFORE_TODAY;
import static pl.edu.pja.trainmate.core.common.error.WorkoutPlanErrorCode.WORKOUT_PLAN_MUST_HAVE_DURATION;
import static pl.edu.pja.trainmate.core.common.error.WorkoutPlanErrorCode.WORKOUT_PLAN_MUST_HAVE_START_DATE;

import java.time.LocalDate;
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

    public WorkoutPlanProjection getCurrentWorkoutPlanProjection(UserId userId) {
        return queryService.getCurrentWorkoutPlan(userId);
    }

    public ResultDto<Long> create(WorkoutPlanCreateDto workoutPlanCreateDto) {
        validateDto(workoutPlanCreateDto);

        var entity = buildWorkoutPlanEntity(workoutPlanCreateDto);
        return ResultDto.ofValueOrError(workoutPlanRepository.save(entity).getId(), COULD_NOT_CREATE_WORKOUT_PLAN);
    }

    public void update(WorkoutPlanUpdateDto workoutPlanUpdateDto) {
        var workoutPlan = workoutPlanRepository.findExactlyOneById(workoutPlanUpdateDto.getId());
        workoutPlan.validateVersion(workoutPlanUpdateDto.getVersion());
        workoutPlan.checkIfModificationAllowed();
        validateDto(workoutPlanUpdateDto);

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
