package pl.edu.pja.trainmate.core.infrastructure.querydsl;

import com.querydsl.core.BooleanBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BaseJpaQueryService;
import pl.edu.pja.trainmate.core.domain.exercise.QExerciseEntity;
import pl.edu.pja.trainmate.core.domain.exercise.QExerciseItemEntity;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.ExerciseItemProjection;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.QExerciseItemProjection;
import pl.edu.pja.trainmate.core.domain.training.QTrainingUnitEntity;
import pl.edu.pja.trainmate.core.domain.training.querydsl.QTrainingUnitProjection;
import pl.edu.pja.trainmate.core.domain.training.querydsl.TrainingUnitProjection;
import pl.edu.pja.trainmate.core.domain.training.querydsl.TrainingUnitQueryService;

@Service
@RequiredArgsConstructor
class QueryDslTrainingUnitQueryService extends BaseJpaQueryService implements TrainingUnitQueryService {

    private static final QTrainingUnitEntity trainingUnit = QTrainingUnitEntity.trainingUnitEntity;
    private static final QExerciseItemEntity exerciseItem = QExerciseItemEntity.exerciseItemEntity;
    private static final QExerciseEntity exercise = QExerciseEntity.exerciseEntity;

    @Override
    public List<TrainingUnitProjection> getTrainingUnitsByWorkoutPlanIdAndWeekNumber(Long workoutPlanId, Long weekNumber) {
        var trainingUnits = queryFactory()
            .select(new QTrainingUnitProjection(
                trainingUnit.id,
                trainingUnit.version,
                trainingUnit.dayOfWeek,
                trainingUnit.weekNumber
            ))
            .from(trainingUnit)
            .where(new BooleanBuilder()
                .and(trainingUnit.workoutPlanId.eq(workoutPlanId))
                .and(trainingUnit.weekNumber.eq(weekNumber))
            )
            .fetch();

        return setReferencesForTrainingUnits(trainingUnits).stream()
            .sorted(Comparator.comparingInt(unit -> unit.getDayOfWeek().getValue()))
            .collect(Collectors.toList());
    }

    @Override
    public Long checkExerciseItemsExistenceByWorkoutPlanIdAndWeekNumber(Long workoutPlanId, Long weekNumber) {
        return queryFactory()
            .select(exerciseItem.id)
            .from(exerciseItem)
            .leftJoin(trainingUnit).on(trainingUnit.workoutPlanId.eq(workoutPlanId))
            .where(new BooleanBuilder()
                .and(exerciseItem.workoutPlanId.eq(workoutPlanId))
                .and(trainingUnit.weekNumber.eq(weekNumber))
            )
            .fetchCount();
    }

    private List<TrainingUnitProjection> setReferencesForTrainingUnits(List<TrainingUnitProjection> trainingUnits) {
        var ids = trainingUnits.stream()
            .map(TrainingUnitProjection::getId)
            .collect(Collectors.toList());

        var exerciseItems = fetchExerciseItemsProjectionForTrainingUnits(ids);

        return trainingUnits.stream()
            .peek(it -> it.addExercises(Optional.ofNullable(exerciseItems.get(it.getId())).orElseGet(ArrayList::new)))
            .collect(Collectors.toList());
    }

    private Map<Long, List<ExerciseItemProjection>> fetchExerciseItemsProjectionForTrainingUnits(List<Long> ids) {
        return queryFactory()
            .select(new QExerciseItemProjection(
                exerciseItem.id,
                exerciseItem.version,
                exerciseItem.exerciseId,
                exerciseItem.volume.repetitions,
                exerciseItem.volume.tempo,
                exerciseItem.volume.weight,
                exerciseItem.volume.rir,
                exerciseItem.volume.sets,
                exercise.muscleInvolved,
                exercise.name,
                exercise.description,
                exercise.url,
                exerciseItem.reported,
                exerciseItem.trainingUnitId
            ))
            .from(exerciseItem)
            .join(exercise).on(exerciseItem.exerciseId.eq(exercise.id))
            .where(new BooleanBuilder()
                .and(exerciseItem.trainingUnitId.in(ids))
            )
            .fetch()
            .stream()
            .collect(Collectors.groupingBy(ExerciseItemProjection::getTrainingUnitId));
    }
}
