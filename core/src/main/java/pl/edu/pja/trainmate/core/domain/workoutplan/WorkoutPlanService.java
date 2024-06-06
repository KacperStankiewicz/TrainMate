package pl.edu.pja.trainmate.core.domain.workoutplan;

import static pl.edu.pja.trainmate.core.common.error.WorkoutPlanErrorCode.COULD_NOT_CREATE_WORKOUT_PLAN;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseItemRepository;
import pl.edu.pja.trainmate.core.domain.training.TrainingUnitRepository;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.AllWorkoutData;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanCreateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanDto;
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
        var entity = buildWorkoutPlanEntity(workoutPlanCreateDto);
        return ResultDto.ofValueOrError(workoutPlanRepository.save(entity).getId(), COULD_NOT_CREATE_WORKOUT_PLAN);
    }

    public void update(WorkoutPlanDto workoutPlanDto) {
        var workoutPlan = workoutPlanRepository.findExactlyOneById(workoutPlanDto.getId());
        workoutPlan.update(workoutPlanDto);
        workoutPlanRepository.saveAndFlush(workoutPlan);
    }

    public void delete(Long id) {
        workoutPlanRepository.deleteById(id);
        exerciseItemRepository.deleteByWorkoutPlanId(id);
        trainingUnitRepository.deleteByWorkoutPlanId(id);
    }

    private WorkoutPlanEntity buildWorkoutPlanEntity(WorkoutPlanCreateDto workoutPlanCreateDto) {
        return WorkoutPlanEntity.builder()
            .name(workoutPlanCreateDto.getName())
            .userId(UserId.valueOf(workoutPlanCreateDto.getUserId()))
            .category(workoutPlanCreateDto.getCategory())
            .dateRange(workoutPlanCreateDto.getDateRange())
            .build();
    }
}
