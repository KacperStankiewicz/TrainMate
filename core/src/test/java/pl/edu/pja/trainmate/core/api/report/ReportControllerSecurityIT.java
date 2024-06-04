package pl.edu.pja.trainmate.core.api.report;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static pl.edu.pja.trainmate.core.api.data.ReportSampleData.getExerciseReportSampleDataBuilder;
import static pl.edu.pja.trainmate.core.api.data.ReportSampleData.getSamplePeriodicalReportCreateDtoBuilder;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.EXERCISE_REPORT;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.EXERCISE_REPORT_REVIEW;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.WORKOUT_PLAN_REPORT;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.WORKOUT_PLAN_REPORT_REVIEW;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.exception.SecurityException;

@SpringBootTest
@AutoConfigureMockMvc
class ReportControllerSecurityIT extends ControllerSpecification {

    private static final Long id = 1L;

    @Test
    void shouldNotAllowAccessForPersonalTrainerWhenCreatingReport() {
        userWithRole(PERSONAL_TRAINER);
        var response = performPost(format(WORKOUT_PLAN_REPORT, id), getSamplePeriodicalReportCreateDtoBuilder().build());

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForPersonalTrainerWhenCreatingExerciseReport() {
        userWithRole(PERSONAL_TRAINER);
        var response = performPost(format(EXERCISE_REPORT, id), getExerciseReportSampleDataBuilder().build());

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenReviewingReport() {
        userWithRole(TRAINED_PERSON);
        var response = performPost(format(WORKOUT_PLAN_REPORT_REVIEW, id));

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenReviewingExerciseReport() {
        userWithRole(TRAINED_PERSON);
        var response = performPost(format(EXERCISE_REPORT_REVIEW, id));

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

}