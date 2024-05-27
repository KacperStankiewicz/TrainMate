package pl.edu.pja.trainmate.core.api.exercise;

import static pl.edu.pja.trainmate.core.api.data.ExerciseSampleData.getCreateDtoBuilder;
import static pl.edu.pja.trainmate.core.api.data.ExerciseSampleData.getExerciseDtoBuilder;
import static pl.edu.pja.trainmate.core.api.data.ExerciseSampleData.getSearchCriteriaBuilder;
import static pl.edu.pja.trainmate.core.api.exercise.ExerciseEndpoints.CREATE;
import static pl.edu.pja.trainmate.core.api.exercise.ExerciseEndpoints.DELETE;
import static pl.edu.pja.trainmate.core.api.exercise.ExerciseEndpoints.SEARCH;
import static pl.edu.pja.trainmate.core.api.exercise.ExerciseEndpoints.UPDATE;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.exception.SecurityException;

@SpringBootTest
@AutoConfigureMockMvc
class ExerciseControllerSecurityIT extends ControllerSpecification {

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenSearchingExercises() {
        //given
        userWithRole(TRAINED_PERSON);
        var criteria = getSearchCriteriaBuilder().build();

        //when
        var response = performPost(SEARCH, criteria);

        //then
        var exception = (SecurityException) response.getResolvedException();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenCreatingExercise() {
        //given
        userWithRole(TRAINED_PERSON);
        var dto = getCreateDtoBuilder().build();

        //when
        var response = performPost(CREATE, dto);

        //then
        var exception = (SecurityException) response.getResolvedException();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenUpdatingExercise() {
        //given
        userWithRole(TRAINED_PERSON);
        var id = 1L;
        var dto = getExerciseDtoBuilder().build();

        //when
        var response = performPut(String.format(UPDATE, id), dto);

        //then
        var exception = (SecurityException) response.getResolvedException();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenDeletingExercise() {
        //given
        userWithRole(TRAINED_PERSON);
        var id = 1L;

        //when
        var response = performDelete(String.format(DELETE, id));

        //then
        var exception = (SecurityException) response.getResolvedException();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

}