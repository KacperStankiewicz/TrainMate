package pl.edu.pja.trainmate.core.api.trainingunit;

import static pl.edu.pja.trainmate.core.api.data.TrainingUnitSampleData.getSampleTrainingUnitDto;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.CREATE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.DELETE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.EXERCISE_ITEM_DELETE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.EXERCISE_ITEM_UPDATE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.UPDATE;
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
class TrainingUnitControllerSecurityIT extends ControllerSpecification {

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenCreatingTraining() {
        userWithRole(TRAINED_PERSON);

        var response = performPost(CREATE, getSampleTrainingUnitDto());

        var exception = (SecurityException) response.getResolvedException();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenUpdatingTraining() {
        userWithRole(TRAINED_PERSON);
        var id = 1L;
        var response = performPut(String.format(UPDATE, id), getSampleTrainingUnitDto());

        var exception = (SecurityException) response.getResolvedException();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenDeletingTraining() {
        userWithRole(TRAINED_PERSON);
        var id = 1L;
        var response = performDelete(String.format(DELETE, id));

        var exception = (SecurityException) response.getResolvedException();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenUpdatingExerciseItem() {
        userWithRole(TRAINED_PERSON);
        var id = 1L;
        var response = performPut(String.format(EXERCISE_ITEM_UPDATE, id), getSampleTrainingUnitDto());

        var exception = (SecurityException) response.getResolvedException();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenDeletingExerciseItem() {
        userWithRole(TRAINED_PERSON);
        var id = 1L;
        var response = performDelete(String.format(EXERCISE_ITEM_DELETE, id));

        var exception = (SecurityException) response.getResolvedException();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

}