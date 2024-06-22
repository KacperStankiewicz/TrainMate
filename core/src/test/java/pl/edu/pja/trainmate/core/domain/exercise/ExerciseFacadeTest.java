package pl.edu.pja.trainmate.core.domain.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.edu.pja.trainmate.core.common.Muscle.BICEPS;
import static pl.edu.pja.trainmate.core.common.Muscle.MuscleGroup.LEGS;
import static pl.edu.pja.trainmate.core.common.error.ExerciseErrorCode.INVALID_SEARCH_CRITERIA;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.ExerciseQueryService;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.ExerciseSearchCriteria;

class ExerciseFacadeTest {

    @MockBean
    private ExerciseQueryService queryService;

    @MockBean
    private ExerciseService service;

    private final ExerciseFacade facade = new ExerciseFacade(queryService, service);

    @Test
    void shouldThrowExceptionWhenMuscleNotInMuscleGroup() {
        //given
        var criteria = new ExerciseSearchCriteria("", BICEPS, LEGS);
        var pageable = Pageable.unpaged();

        //then
        var exception = assertThrows(CommonException.class, () -> facade.searchByCriteria(criteria, pageable));
        assertEquals(INVALID_SEARCH_CRITERIA.getCode(), exception.getCode());
        assertEquals(INVALID_SEARCH_CRITERIA.getMessage(), exception.getMessage());
        assertEquals(INVALID_SEARCH_CRITERIA.getHttpStatus().value(), exception.getStatus().value());
    }
}