package pl.edu.pja.trainmate.core.domain.workoutplan.exercise.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pja.trainmate.core.domain.workoutplan.exercise.dto.ExerciseListItemProjection;

public interface ExerciseQueryService {

    Page<ExerciseListItemProjection> searchByCriteria(ExerciseSearchCriteria criteria, Pageable pageable);
}
