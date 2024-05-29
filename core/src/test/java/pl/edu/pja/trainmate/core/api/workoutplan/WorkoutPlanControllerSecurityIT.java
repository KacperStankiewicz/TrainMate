package pl.edu.pja.trainmate.core.api.workoutplan;

import static pl.edu.pja.trainmate.core.api.data.WorkoutPlanSampleData.getWorkoutPlanCreateDto;
import static pl.edu.pja.trainmate.core.api.data.WorkoutPlanSampleData.getWorkoutPlanDto;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.CREATE;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.DELETE;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.GET;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.UPDATE;
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
class WorkoutPlanControllerSecurityIT extends ControllerSpecification {

    @Test
    void shouldNotAllowAccessForPersonWithoutRoleWhenSearchingExercises() {
        //given
        var id = 1L;
        var response = performGet(String.format(GET, id));

        //when
        var exception = (SecurityException) response.getResolvedException();

        //then
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenCreatingWorkoutPlan() {
        //given
        userWithRole(TRAINED_PERSON);
        var dto = getWorkoutPlanCreateDto();

        //when
        var response = performPost(CREATE, dto);

        //then
        var exception = (SecurityException) response.getResolvedException();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenUpdatingWorkoutPlan() {
        //given
        userWithRole(TRAINED_PERSON);
        var id = 1L;
        var dto = getWorkoutPlanDto();

        //when
        var response = performPut(String.format(UPDATE, id), dto);

        //then
        var exception = (SecurityException) response.getResolvedException();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenDeletingWorkoutPlan() {
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