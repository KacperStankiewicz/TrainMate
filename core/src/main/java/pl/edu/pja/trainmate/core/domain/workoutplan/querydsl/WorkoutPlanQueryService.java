package pl.edu.pja.trainmate.core.domain.workoutplan.querydsl;

import java.util.List;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.AllWorkoutData;

public interface WorkoutPlanQueryService {

    AllWorkoutData getWorkoutPlanData(Long workoutPlanId);

    WorkoutPlanProjection getCurrentWorkoutPlan(UserId loggedUserId);

    List<String> getUsersEmailsForEndedWorkoutPlanWithoutReport();

    List<String> getUsersEmailsWithActiveWorkoutPlan();
}
