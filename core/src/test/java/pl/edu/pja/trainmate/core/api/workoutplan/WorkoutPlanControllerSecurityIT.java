package pl.edu.pja.trainmate.core.api.workoutplan;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static pl.edu.pja.trainmate.core.api.sampledata.WorkoutPlanSampleData.getSampleWorkoutPlanCreateDtoBuilder;
import static pl.edu.pja.trainmate.core.api.sampledata.WorkoutPlanSampleData.getSampleWorkoutPlanDtoBuilder;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.CREATE;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.DELETE;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.GET;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.UPDATE;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.exception.SecurityException;

@SpringBootTest
@AutoConfigureMockMvc
class WorkoutPlanControllerSecurityIT extends ControllerSpecification {

    private static final Long ID = 1L;

    @Test
    void shouldNotAllowAccessForPersonWithoutRoleWhenSearchingExercises() {
        //given
        var response = performGet(format(GET, ID));

        //when
        var exception = (SecurityException) response.getResolvedException();

        //then
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenCreatingWorkoutPlan() {
        //given
        userWithRole(TRAINED_PERSON);
        var dto = getSampleWorkoutPlanCreateDtoBuilder().build();

        //when
        var response = performPost(CREATE, dto);

        //then
        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenUpdatingWorkoutPlan() {
        //given
        userWithRole(TRAINED_PERSON);
        var dto = getSampleWorkoutPlanDtoBuilder().build();

        //when
        var response = performPut(format(UPDATE, ID), dto);

        //then
        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenDeletingWorkoutPlan() {
        //given
        userWithRole(TRAINED_PERSON);

        //when
        var response = performDelete(format(DELETE, ID), BasicAuditDto.ofValue(ID, 0L));

        //then
        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }
}