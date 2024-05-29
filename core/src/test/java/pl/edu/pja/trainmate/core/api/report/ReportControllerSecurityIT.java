package pl.edu.pja.trainmate.core.api.report;

import static pl.edu.pja.trainmate.core.api.data.ReportSampleData.getExerciseReportSampleData;
import static pl.edu.pja.trainmate.core.api.data.ReportSampleData.getSamplePeriodicalReportCreateDto;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.EXERCISE_REPORT;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.EXERCISE_REPORT_REVIEW;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.WORKOUT_PLAN_REPORT;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.WORKOUT_PLAN_REPORT_REVIEW;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
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
class ReportControllerSecurityIT extends ControllerSpecification {

    @Test
    void shouldNotAllowAccessForPersonalTrainerWhenCreatingReport() {
        userWithRole(PERSONAL_TRAINER);
        var id = 1L;
        var response = performPost(String.format(WORKOUT_PLAN_REPORT, id), getSamplePeriodicalReportCreateDto());

        var exception = (SecurityException) response.getResolvedException();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForPersonalTrainerWhenCreatingExerciseReport() {
        userWithRole(PERSONAL_TRAINER);
        var id = 1L;
        var response = performPost(String.format(EXERCISE_REPORT, id), getExerciseReportSampleData());

        var exception = (SecurityException) response.getResolvedException();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenReviewingReport() {
        userWithRole(TRAINED_PERSON);
        var id = 1L;
        var response = performPost(String.format(WORKOUT_PLAN_REPORT_REVIEW, id));

        var exception = (SecurityException) response.getResolvedException();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenReviewingExerciseReport() {
        userWithRole(TRAINED_PERSON);
        var id = 1L;
        var response = performPost(String.format(EXERCISE_REPORT_REVIEW, id));

        var exception = (SecurityException) response.getResolvedException();
        Assertions.assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

}