package pl.edu.pja.trainmate.core.domain.training.querydsl;

import java.util.List;

public interface TrainingUnitQueryService {

    List<TrainingUnitProjection> getTrainingUnitsForCurrentWeek(Long workoutPlanId, Long weekNumber);
}
