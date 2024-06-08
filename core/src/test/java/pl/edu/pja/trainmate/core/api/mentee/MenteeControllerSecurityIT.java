package pl.edu.pja.trainmate.core.api.mentee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static pl.edu.pja.trainmate.core.api.mentee.MenteeEndpoints.ACTIVATE;
import static pl.edu.pja.trainmate.core.api.mentee.MenteeEndpoints.DEACTIVATE;
import static pl.edu.pja.trainmate.core.api.mentee.MenteeEndpoints.INITIAL_REPORT;
import static pl.edu.pja.trainmate.core.api.mentee.MenteeEndpoints.INVITE;
import static pl.edu.pja.trainmate.core.api.mentee.MenteeEndpoints.SEARCH;
import static pl.edu.pja.trainmate.core.api.sampledata.ReportSampleData.getSamplePeriodicalReportCreateDtoBuilder;
import static pl.edu.pja.trainmate.core.common.Gender.FEMALE;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.NumberRange;
import pl.edu.pja.trainmate.core.common.exception.SecurityException;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeSearchCriteria;

@SpringBootTest
@AutoConfigureMockMvc
class MenteeControllerSecurityIT extends ControllerSpecification {

    @Test
    void shouldAllowAccessForTrainedPersonWhenSearchingMentees() {
        userWithRole(TRAINED_PERSON);
        var criteria = new MenteeSearchCriteria("", "", "", new NumberRange(1L, 2L), FEMALE);
        var response = performPost(SEARCH, criteria);

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldAllowAccessForTrainedPersonWhenInvitingMentee() {
        userWithRole(TRAINED_PERSON);
        var email = "test@test.com";
        var response = performPost(INVITE, "email", email);

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldAllowAccessForPersonalTrainerWhenCreatingInitialReport() {
        userWithRole(PERSONAL_TRAINER);
        var dto = getSamplePeriodicalReportCreateDtoBuilder().build();
        var response = performPost(INITIAL_REPORT, dto);

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldAllowAccessForTrainedPersonWhenDeactivatingMenteeAccount() {
        userWithRole(TRAINED_PERSON);
        var id = 1L;
        var response = performPost(String.format(DEACTIVATE, id), BasicAuditDto.ofValue(id, 0L));

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }

    @Test
    void shouldAllowAccessForTrainedPersonWhenActivatingMenteeAccount() {
        userWithRole(TRAINED_PERSON);
        var id = 1L;
        var response = performPost(String.format(ACTIVATE, id), BasicAuditDto.ofValue(id, 0L));

        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }
}
