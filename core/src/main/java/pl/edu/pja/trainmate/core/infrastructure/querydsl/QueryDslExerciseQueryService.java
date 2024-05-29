package pl.edu.pja.trainmate.core.infrastructure.querydsl;

import static pl.edu.pja.trainmate.core.common.Muscle.getAllMusclesByGroup;

import com.querydsl.core.BooleanBuilder;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BaseJpaQueryService;
import pl.edu.pja.trainmate.core.common.OrderByBuilder;
import pl.edu.pja.trainmate.core.domain.exercise.QExerciseEntity;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseListItemProjection;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseProjection;
import pl.edu.pja.trainmate.core.domain.exercise.dto.QExerciseListItemProjection;
import pl.edu.pja.trainmate.core.domain.exercise.dto.QExerciseProjection;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.ExerciseQueryService;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.ExerciseSearchCriteria;

@Service
@RequiredArgsConstructor
class QueryDslExerciseQueryService extends BaseJpaQueryService implements ExerciseQueryService {

    private static final QExerciseEntity exercise = QExerciseEntity.exerciseEntity;

    @Override
    public Page<ExerciseListItemProjection> searchByCriteria(ExerciseSearchCriteria criteria, Pageable pageable) {
        var query = queryFactory()
            .select(new QExerciseListItemProjection(
                exercise.id,
                exercise.name,
                exercise.muscleInvolved
            ))
            .from(exercise)
            .where(new BooleanBuilder()
                .and(isLike(exercise.name, criteria.getName()))
                .and(equals(exercise.muscleInvolved, criteria.getMuscle()))
                .and(isMemberOf(exercise.muscleInvolved, getAllMusclesByGroup(criteria.getMuscleGroup())))
            )
            .orderBy(OrderByBuilder.with(pageable.getSort())
                .whenPropertyIs("name").thenSortBy(exercise.name)
                .and().whenPropertyIs("muscle").thenSortBy(exercise.muscleInvolved)
                .and().defaultSortBy(exercise.name.asc())
                .build());

        return fetchPage(query, pageable);
    }

    @Override
    public ExerciseProjection getExerciseProjectionById(Long id) {
        var result = queryFactory()
            .select(new QExerciseProjection(
                exercise.id,
                exercise.name,
                exercise.description,
                exercise.url,
                exercise.muscleInvolved
            ))
            .from(exercise)
            .where(exercise.id.eq(id))
            .fetchOne();

        return Optional.ofNullable(result)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("Could not find exercise for id: %s", id))
            );
    }
}
