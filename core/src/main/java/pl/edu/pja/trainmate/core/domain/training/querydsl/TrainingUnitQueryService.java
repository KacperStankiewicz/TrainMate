package pl.edu.pja.trainmate.core.domain.training.querydsl;

import java.util.List;

public interface TrainingUnitQueryService {

    List<TrainingUnitProjection> getTrainingUnitsByWorkoutPlanIdAndWeekNumber(Long workoutPlanId, Long weekNumber);
}
