package pl.edu.pja.trainmate.core.domain.training;

import java.util.List;
import org.springframework.stereotype.Repository;
import pl.edu.pja.trainmate.core.common.BaseRepository;

@Repository
public interface TrainingUnitRepository extends BaseRepository<TrainingUnitEntity> {

    void deleteByWorkoutPlanId(Long id);
}
