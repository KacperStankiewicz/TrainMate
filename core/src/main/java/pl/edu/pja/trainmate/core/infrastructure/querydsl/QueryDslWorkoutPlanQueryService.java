package pl.edu.pja.trainmate.core.infrastructure.querydsl;

import static com.querydsl.jpa.JPAExpressions.selectFrom;
import static pl.edu.pja.trainmate.core.common.error.WorkoutPlanErrorCode.USER_DOES_NOT_HAVE_ANY_WORKOUT_PLAN;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BaseJpaQueryService;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataDto;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataProvider;
import pl.edu.pja.trainmate.core.domain.exercise.QExerciseEntity;
import pl.edu.pja.trainmate.core.domain.exercise.QExerciseItemEntity;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.ExerciseItemProjection;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.QExerciseItemProjection;
import pl.edu.pja.trainmate.core.domain.report.QReportEntity;
import pl.edu.pja.trainmate.core.domain.report.querydsl.QPeriodicalReportBasicInfoProjection;
import pl.edu.pja.trainmate.core.domain.training.QTrainingUnitEntity;
import pl.edu.pja.trainmate.core.domain.training.querydsl.QTrainingUnitProjection;
import pl.edu.pja.trainmate.core.domain.training.querydsl.TrainingUnitProjection;
import pl.edu.pja.trainmate.core.domain.user.QUserEntity;
import pl.edu.pja.trainmate.core.domain.workoutplan.QWorkoutPlanEntity;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.AllWorkoutData;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.QAllWorkoutData;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.QWorkoutPlanListItemProjection;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.QWorkoutPlanProjection;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanListItemProjection;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanProjection;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanQueryService;

@Service
@RequiredArgsConstructor
class QueryDslWorkoutPlanQueryService extends BaseJpaQueryService implements WorkoutPlanQueryService {

    private static final QWorkoutPlanEntity workoutPlan = QWorkoutPlanEntity.workoutPlanEntity;
    private static final QTrainingUnitEntity trainingUnit = QTrainingUnitEntity.trainingUnitEntity;
    private static final QExerciseItemEntity exerciseItem = QExerciseItemEntity.exerciseItemEntity;
    private static final QExerciseEntity exercise = QExerciseEntity.exerciseEntity;
    private static final QReportEntity report = QReportEntity.reportEntity;
    private static final QUserEntity user = QUserEntity.userEntity;

    private final LoggedUserDataProvider userDataProvider;

    @Override
    public AllWorkoutData getWorkoutPlanData(Long workoutPlanId) {
        var userDetails = userDataProvider.getUserDetails();

        var workoutData = queryFactory()
            .select(new QAllWorkoutData(
                workoutPlan.id,
                workoutPlan.version,
                workoutPlan.name,
                workoutPlan.category
            ))
            .from(workoutPlan)
            .where(new BooleanBuilder()
                .and(workoutPlan.id.eq(workoutPlanId))
                .and(prepareUserPredicate(userDetails))
            )
            .fetchOne();

        if (workoutData == null) {
            throw new CommonException(USER_DOES_NOT_HAVE_ANY_WORKOUT_PLAN);
        }

        var trainingUnits = fetchTrainingUnitsProjection(workoutPlanId);
        workoutData.addTrainingUnits(trainingUnits);
        return workoutData;
    }

    @Override
    public List<WorkoutPlanListItemProjection> getAllWorkoutPlansByUserId(UserId userId) {
        return queryFactory()
            .select(new QWorkoutPlanListItemProjection(
                workoutPlan.id,
                workoutPlan.name,
                workoutPlan.dateRange,
                new QPeriodicalReportBasicInfoProjection(
                    report.id,
                    report.reviewed
                )
            ))
            .from(workoutPlan)
            .leftJoin(report).on(report.workoutPlanId.eq(workoutPlan.id))
            .where(new BooleanBuilder()
                .and(workoutPlan.userId.eq(userId))
            )
            .orderBy(workoutPlan.dateRange.from.desc())
            .fetch();
    }

    @Override
    public WorkoutPlanProjection getCurrentWorkoutPlan(UserId loggedUserId) {
        return queryFactory()
            .select(buildWorkoutPlanProjection())
            .from(workoutPlan)
            .where(new BooleanBuilder()
                .and(workoutPlan.userId.eq(loggedUserId))
                .and(workoutPlan.dateRange.from.loe(LocalDate.now()))
                .and(workoutPlan.dateRange.to.goe(LocalDate.now()))
            )
            .fetchOne();
    }

    @Override
    public WorkoutPlanProjection getWorkoutPlanHeader(Long workoutPlanId) {
        return queryFactory()
            .select(buildWorkoutPlanProjection())
            .from(workoutPlan)
            .where(new BooleanBuilder()
                .and(workoutPlan.id.eq(workoutPlanId))
            )
            .fetchOne();
    }

    @Override
    public List<String> getUsersEmailsForEndedWorkoutPlanWithoutReport() {
        return queryFactory()
            .select(user.personalInfo.email)
            .from(workoutPlan)
            .leftJoin(user).on(user.userId.keycloakId.eq(workoutPlan.userId.keycloakId))
            .where(new BooleanBuilder()
                .and(workoutPlan.dateRange.to.loe(LocalDate.now()))
                .and(selectFrom(report).where(report.workoutPlanId.eq(workoutPlan.id)).notExists())
            )
            .fetch();
    }

    @Override
    public List<String> getUsersEmailsWithActiveWorkoutPlan() {
        return queryFactory()
            .select(user.personalInfo.email)
            .from(workoutPlan)
            .leftJoin(user).on(user.userId.keycloakId.eq(workoutPlan.userId.keycloakId))
            .leftJoin(trainingUnit).on(trainingUnit.workoutPlanId.eq(workoutPlan.id))
            .where(new BooleanBuilder()
                .and(workoutPlan.dateRange.to.goe(LocalDate.now()))
                .and(workoutPlan.dateRange.from.loe(LocalDate.now()))
                .and(selectFrom(trainingUnit)
                    .where(new BooleanBuilder()
                        .and(trainingUnit.completed.isTrue())
                    ).exists())
            )
            .fetch();
    }

    private Predicate prepareUserPredicate(LoggedUserDataDto userDetails) {
        var predicate = new BooleanBuilder();

        if (PERSONAL_TRAINER.equals(userDetails.getRole())) {
            return predicate;
        }

        return predicate.and(workoutPlan.userId.keycloakId.eq(userDetails.getUserId().getKeycloakId()));
    }

    private List<TrainingUnitProjection> fetchTrainingUnitsProjection(Long workoutPlanId) {
        var trainingUnits = queryFactory()
            .select(new QTrainingUnitProjection(
                trainingUnit.id,
                trainingUnit.version,
                trainingUnit.dayOfWeek,
                trainingUnit.weekNumber
            ))
            .from(trainingUnit)
            .where(trainingUnit.workoutPlanId.eq(workoutPlanId))
            .fetch();

        var exerciseItems = fetchExerciseItemsProjection(workoutPlanId);

        trainingUnits.forEach(it -> it.addExercises(exerciseItems.get(it.getId())));

        return new ArrayList<>(trainingUnits);
    }

    private Map<Long, List<ExerciseItemProjection>> fetchExerciseItemsProjection(Long workoutPlanId) {
        return queryFactory()
            .select(new QExerciseItemProjection(
                exerciseItem.id,
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
            .where(exerciseItem.workoutPlanId.eq(workoutPlanId))
            .fetch()
            .stream()
            .collect(Collectors.groupingBy(ExerciseItemProjection::getTrainingUnitId));
    }

    private static QWorkoutPlanProjection buildWorkoutPlanProjection() {
        return new QWorkoutPlanProjection(
            workoutPlan.id,
            workoutPlan.version,
            workoutPlan.name,
            workoutPlan.category,
            workoutPlan.dateRange
        );
    }
}
