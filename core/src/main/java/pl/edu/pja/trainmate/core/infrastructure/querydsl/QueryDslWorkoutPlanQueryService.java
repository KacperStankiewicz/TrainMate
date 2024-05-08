package pl.edu.pja.trainmate.core.infrastructure.querydsl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BaseJpaQueryService;
import pl.edu.pja.trainmate.core.domain.exercise.QExerciseEntity;
import pl.edu.pja.trainmate.core.domain.exercise.QExerciseItemEntity;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.ExerciseItemProjection;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.QExerciseItemProjection;
import pl.edu.pja.trainmate.core.domain.training.QTrainingUnitEntity;
import pl.edu.pja.trainmate.core.domain.training.querydsl.QTrainingUnitProjection;
import pl.edu.pja.trainmate.core.domain.training.querydsl.TrainingUnitProjection;
import pl.edu.pja.trainmate.core.domain.workoutplan.QWorkoutPlanEntity;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.AllWorkoutData;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.QAllWorkoutData;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanQueryService;

@Service
public class QueryDslWorkoutPlanQueryService extends BaseJpaQueryService implements WorkoutPlanQueryService {

    private static final QWorkoutPlanEntity workoutPlan = QWorkoutPlanEntity.workoutPlanEntity;
    private static final QTrainingUnitEntity trainingUnit = QTrainingUnitEntity.trainingUnitEntity;
    private static final QExerciseItemEntity exerciseItem = QExerciseItemEntity.exerciseItemEntity;
    private static final QExerciseEntity exercise = QExerciseEntity.exerciseEntity;

    @Override
    public AllWorkoutData getWorkoutPlanData(Long workoutPlanId) {
        var workoutData = queryFactory()
            .select(new QAllWorkoutData(
                workoutPlan.id,
                workoutPlan.name,
                workoutPlan.category
            ))
            .from(workoutPlan)
            .where(workoutPlan.id.eq(workoutPlanId))
            .fetchOne();

        var trainingUnits = fetchTrainingUnitsProjection(workoutPlanId);
        workoutData.addTrainingUnits(trainingUnits);
        return workoutData;
    }

    private List<TrainingUnitProjection> fetchTrainingUnitsProjection(Long workoutPlanId) {
        var trainingUnits = queryFactory()
            .select(new QTrainingUnitProjection(
                trainingUnit.id,
                trainingUnit.dayOfWeek,
                trainingUnit.weekNumber
            ))
            .from(trainingUnit)
            .where(trainingUnit.workoutPlanId.eq(workoutPlanId))
            .fetchAll();

        var exerciseItems = fetchExerciseItemsProjection(workoutPlanId);
        return trainingUnits.stream()
            .peek(it -> {
                var exercises = exerciseItems.get(it.getId());
                it.addExercises(exercises);
            })
            .collect(Collectors.toList());
    }

    private Map<Long, List<ExerciseItemProjection>> fetchExerciseItemsProjection(Long workoutPlanId) {
        return queryFactory()
            .select(new QExerciseItemProjection(
                exerciseItem.id,
                exerciseItem.volume.repetitions,
                exerciseItem.volume.tempo,
                exerciseItem.volume.weight,
                exerciseItem.volume.rir,
                exerciseItem.volume.sets,
                exercise.muscleInvolved,
                exercise.name,
                exercise.description,
                exercise.url,
                exerciseItem.trainingUnitId
            ))
            .from(exerciseItem)
            .join(exercise).on(exerciseItem.exerciseId.eq(exercise.id))
            .where(exerciseItem.workoutPlanId.eq(workoutPlanId))
            .fetch()
            .stream()
            .collect(Collectors.groupingBy(ExerciseItemProjection::getTrainingUnitId));
    }
}
