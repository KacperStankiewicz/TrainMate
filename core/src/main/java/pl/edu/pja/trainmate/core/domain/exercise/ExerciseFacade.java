package pl.edu.pja.trainmate.core.domain.exercise;

import static pl.edu.pja.trainmate.core.common.error.ExerciseErrorCode.INVALID_SEARCH_CRITERIA;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseCreateDto;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseDto;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseListItemProjection;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseProjection;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.ExerciseQueryService;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.ExerciseSearchCriteria;

@Service
@RequiredArgsConstructor
public class ExerciseFacade {

    private final ExerciseQueryService queryService;
    private final ExerciseService service;

    public Page<ExerciseListItemProjection> searchByCriteria(ExerciseSearchCriteria criteria, Pageable pageable) {
        validateSearchCriteria(criteria);
        return queryService.searchByCriteria(criteria, pageable);
    }

    public ExerciseProjection getById(Long id) {
        return queryService.getExerciseProjectionById(id);
    }

    public ResultDto<Long> create(ExerciseCreateDto dto) {
        return service.create(dto);
    }

    public void update(ExerciseDto dto) {
        service.update(dto);
    }

    public void delete(Long exerciseId) {
        service.deleteById(exerciseId);
    }

    private void validateSearchCriteria(ExerciseSearchCriteria criteria) {
        if (criteria.getMuscle() != null && (criteria.getMuscleGroup() != null && !criteria.getMuscle().isInGroup(criteria.getMuscleGroup()))) {
            throw new CommonException(INVALID_SEARCH_CRITERIA);
        }
    }
}
