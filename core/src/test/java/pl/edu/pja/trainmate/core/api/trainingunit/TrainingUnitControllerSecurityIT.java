package pl.edu.pja.trainmate.core.api.trainingunit;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static pl.edu.pja.trainmate.core.api.sampledata.TrainingUnitSampleData.getSampleTrainingUnitDtoBuilder;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.CREATE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.DELETE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.EXERCISE_ITEM_DELETE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.EXERCISE_ITEM_UPDATE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.GET_FOR_CURRENT_WEEK;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.GET_FOR_WEEK;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.UPDATE;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.exception.SecurityException;

@SpringBootTest
@AutoConfigureMockMvc
class TrainingUnitControllerSecurityIT extends ControllerSpecification {

    private static final Long ID = 1L;

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenCreatingTraining() {
        userWithRole(TRAINED_PERSON);

        var response = performPost(CREATE, getSampleTrainingUnitDtoBuilder().build());

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenUpdatingTraining() {
        userWithRole(TRAINED_PERSON);
        var response = performPut(format(UPDATE, ID), getSampleTrainingUnitDtoBuilder().build());

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenDeletingTraining() {
        userWithRole(TRAINED_PERSON);
        var response = performDelete(format(DELETE, ID), BasicAuditDto.ofValue(ID, 0L));

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenUpdatingExerciseItem() {
        userWithRole(TRAINED_PERSON);
        var response = performPut(format(EXERCISE_ITEM_UPDATE, ID), getSampleTrainingUnitDtoBuilder().build());

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenDeletingExerciseItem() {
        userWithRole(TRAINED_PERSON);
        var response = performDelete(format(EXERCISE_ITEM_DELETE, ID), BasicAuditDto.ofValue(ID, 0L));

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenGettingTrainingUnitsByWorkoutPlanIdAndWeekNumber() {
        userWithRole(TRAINED_PERSON);
        var response = performGet(format(GET_FOR_WEEK, ID, 1L));

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForPersonalTrainerWhenGettingTrainingUnitsForCurrentWeek() {
        userWithRole(PERSONAL_TRAINER);
        var response = performGet(GET_FOR_CURRENT_WEEK);

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }
}