package pl.edu.pja.trainmate.core.domain.workoutplan.querydsl;

import pl.edu.pja.trainmate.core.domain.workoutplan.dto.AllWorkoutData;

public interface WorkoutPlanQueryService {

    AllWorkoutData getWorkoutPlanData(Long workoutPlanId);
}
