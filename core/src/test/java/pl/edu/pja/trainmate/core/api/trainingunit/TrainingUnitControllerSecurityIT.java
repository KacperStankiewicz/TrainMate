package pl.edu.pja.trainmate.core.api.trainingunit;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static pl.edu.pja.trainmate.core.api.data.TrainingUnitSampleData.getSampleTrainingUnitDtoBuilder;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.CREATE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.DELETE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.EXERCISE_ITEM_DELETE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.EXERCISE_ITEM_UPDATE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.UPDATE;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.exception.SecurityException;

@SpringBootTest
@AutoConfigureMockMvc
class TrainingUnitControllerSecurityIT extends ControllerSpecification {

    private static final Long id = 1L;

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
        var response = performPut(format(UPDATE, id), getSampleTrainingUnitDtoBuilder().build());

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenDeletingTraining() {
        userWithRole(TRAINED_PERSON);
        var response = performDelete(format(DELETE, id));

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenUpdatingExerciseItem() {
        userWithRole(TRAINED_PERSON);
        var response = performPut(format(EXERCISE_ITEM_UPDATE, id), getSampleTrainingUnitDtoBuilder().build());

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenDeletingExerciseItem() {
        userWithRole(TRAINED_PERSON);
        var response = performDelete(format(EXERCISE_ITEM_DELETE, id));

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }
}