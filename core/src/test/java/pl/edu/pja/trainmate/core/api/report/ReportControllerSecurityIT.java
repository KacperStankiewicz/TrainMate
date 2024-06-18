package pl.edu.pja.trainmate.core.api.report;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.EXERCISE_REPORT;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.WORKOUT_PLAN_REPORT;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.WORKOUT_PLAN_REPORT_REVIEW;
import static pl.edu.pja.trainmate.core.api.sampledata.ReportSampleData.getExerciseReportSampleDataBuilder;
import static pl.edu.pja.trainmate.core.api.sampledata.ReportSampleData.getSamplePeriodicalReportCreateDtoBuilder;
import static pl.edu.pja.trainmate.core.config.security.RoleType.MENTEE;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.exception.SecurityException;

@SpringBootTest
@AutoConfigureMockMvc
class ReportControllerSecurityIT extends ControllerSpecification {

    private static final Long ID = 1L;

    @Test
    void shouldNotAllowAccessForPersonalTrainerWhenCreatingReport() {
        userWithRole(PERSONAL_TRAINER);
        var response = performPost(format(WORKOUT_PLAN_REPORT, ID), getSamplePeriodicalReportCreateDtoBuilder().build());

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForPersonalTrainerWhenCreatingExerciseReport() {
        userWithRole(PERSONAL_TRAINER);
        var response = performPost(format(EXERCISE_REPORT, ID), getExerciseReportSampleDataBuilder().build());

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldNotAllowAccessForTrainedPersonWhenReviewingReport() {
        userWithRole(MENTEE);
        var response = performPost(format(WORKOUT_PLAN_REPORT_REVIEW, ID), BasicAuditDto.ofValue(ID, 0L));

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

}