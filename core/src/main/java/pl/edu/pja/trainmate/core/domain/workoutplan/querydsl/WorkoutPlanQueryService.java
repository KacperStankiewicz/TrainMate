package pl.edu.pja.trainmate.core.domain.workoutplan.querydsl;

import java.util.List;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.AllWorkoutData;

public interface WorkoutPlanQueryService {

    AllWorkoutData getWorkoutPlanData(Long workoutPlanId);

    List<WorkoutPlanListItemProjection> getAllWorkoutPlansByUserId(UserId userId);

    List<String> getUsersEmailsForEndedWorkoutPlanWithoutReport();

    List<String> getUsersEmailsWithActiveWorkoutPlan();

    WorkoutPlanProjection getCurrentWorkoutPlan(UserId loggedUserId);

    WorkoutPlanProjection getWorkoutPlanHeader(Long workoutPlanId);
}
