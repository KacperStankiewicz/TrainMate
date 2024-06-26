package pl.edu.pja.trainmate.core.domain.exercise.querydsl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseListItemProjection;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseProjection;

public interface ExerciseQueryService {

    Page<ExerciseListItemProjection> searchByCriteria(ExerciseSearchCriteria criteria, Pageable pageable);

    ExerciseProjection getExerciseProjectionById(Long id);

    List<ExerciseListItemProjection> getAllExercises();
}
