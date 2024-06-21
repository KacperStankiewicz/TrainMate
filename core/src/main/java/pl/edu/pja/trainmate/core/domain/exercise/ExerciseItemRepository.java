package pl.edu.pja.trainmate.core.domain.exercise;

import org.springframework.stereotype.Repository;
import pl.edu.pja.trainmate.core.common.BaseRepository;

@Repository
public interface ExerciseItemRepository extends BaseRepository<ExerciseItemEntity> {

    void deleteByTrainingUnitId(Long id);

    void deleteAllByWorkoutPlanId(Long id);

    ExerciseItemEntity getExerciseItemEntityByTrainingUnitId(Long trainingUnitId);
}
