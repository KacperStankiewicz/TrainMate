package pl.edu.pja.trainmate.core.infrastructure.querydsl;

import static com.querydsl.jpa.JPAExpressions.selectFrom;

import com.querydsl.core.BooleanBuilder;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BaseJpaQueryService;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.domain.exercise.QExerciseEntity;
import pl.edu.pja.trainmate.core.domain.exercise.QExerciseItemEntity;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.ExerciseItemProjection;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.QExerciseItemProjection;
import pl.edu.pja.trainmate.core.domain.report.QReportEntity;
import pl.edu.pja.trainmate.core.domain.training.QTrainingUnitEntity;
import pl.edu.pja.trainmate.core.domain.training.querydsl.QTrainingUnitProjection;
import pl.edu.pja.trainmate.core.domain.training.querydsl.TrainingUnitProjection;
import pl.edu.pja.trainmate.core.domain.user.QUserEntity;
import pl.edu.pja.trainmate.core.domain.workoutplan.QWorkoutPlanEntity;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.AllWorkoutData;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.QAllWorkoutData;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.QWorkoutPlanProjection;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanProjection;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanQueryService;

@Service
class QueryDslWorkoutPlanQueryService extends BaseJpaQueryService implements WorkoutPlanQueryService {

    private static final QWorkoutPlanEntity workoutPlan = QWorkoutPlanEntity.workoutPlanEntity;
    private static final QTrainingUnitEntity trainingUnit = QTrainingUnitEntity.trainingUnitEntity;
    private static final QExerciseItemEntity exerciseItem = QExerciseItemEntity.exerciseItemEntity;
    private static final QExerciseEntity exercise = QExerciseEntity.exerciseEntity;
    private static final QReportEntity report = QReportEntity.reportEntity;
    private static final QUserEntity user = QUserEntity.userEntity;

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

    @Override
    public WorkoutPlanProjection getCurrentWorkoutPlan(UserId loggedUserId) {
        return queryFactory()
            .select(new QWorkoutPlanProjection(
                workoutPlan.id,
                workoutPlan.name,
                workoutPlan.category,
                workoutPlan.dateRange
            ))
            .from(workoutPlan)
            .where(new BooleanBuilder()
                .and(workoutPlan.userId.eq(loggedUserId))
                .and(workoutPlan.dateRange.from.loe(LocalDate.now()))
                .and(workoutPlan.dateRange.to.goe(LocalDate.now()))
            )
            .fetchOne();
    }

    @Override
    public List<String> getUserEmailsForEndedWorkoutPlanWithoutReport() {
        return queryFactory()
            .select(user.personalInfo.email)
            .from(workoutPlan)
            .where(new BooleanBuilder()
                .and(workoutPlan.dateRange.to.loe(LocalDate.now()))
                .and(selectFrom(report).where(report.workoutPlanId.eq(workoutPlan.id)).notExists())
            ).fetch();
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
            .peek(it -> it.addExercises(exerciseItems.get(it.getId())))
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
