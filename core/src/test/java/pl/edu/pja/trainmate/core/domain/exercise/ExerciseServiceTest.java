package pl.edu.pja.trainmate.core.domain.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.edu.pja.trainmate.core.common.Muscle.MIDDLE_CHEST;
import static pl.edu.pja.trainmate.core.common.error.ExerciseErrorCode.MUSCLE_INVOLVED_MUST_NOT_BE_NULL;
import static pl.edu.pja.trainmate.core.common.error.ExerciseErrorCode.NAME_MUST_NOT_BE_NULL;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseCreateDto;

class ExerciseServiceTest {

    @MockBean
    private ExerciseRepository repository;

    @MockBean
    private ExerciseItemRepository exerciseItemRepository;

    private final ExerciseService service = new ExerciseService(repository, exerciseItemRepository);

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        //given
        var createDto = ExerciseCreateDto.builder()
            .url("test")
            .muscleInvolved(MIDDLE_CHEST)
            .build();

        //then
        var exception = assertThrows(CommonException.class, () -> service.create(createDto));
        assertEquals(NAME_MUST_NOT_BE_NULL.getCode(), exception.getCode());
        assertEquals(NAME_MUST_NOT_BE_NULL.getMessage(), exception.getMessage());
        assertEquals(NAME_MUST_NOT_BE_NULL.getHttpStatus().value(), exception.getStatus().value());
    }

    @Test
    void shouldThrowExceptionWhenMuscleInvolvedIsNull() {
        //given
        var createDto = ExerciseCreateDto.builder()
            .url("test")
            .name("test")
            .build();

        //then
        var exception = assertThrows(CommonException.class, () -> service.create(createDto));
        assertEquals(MUSCLE_INVOLVED_MUST_NOT_BE_NULL.getCode(), exception.getCode());
        assertEquals(MUSCLE_INVOLVED_MUST_NOT_BE_NULL.getMessage(), exception.getMessage());
        assertEquals(MUSCLE_INVOLVED_MUST_NOT_BE_NULL.getHttpStatus().value(), exception.getStatus().value());
    }
}