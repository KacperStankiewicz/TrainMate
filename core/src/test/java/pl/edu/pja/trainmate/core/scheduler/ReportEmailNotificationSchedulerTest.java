package pl.edu.pja.trainmate.core.scheduler;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.edu.pja.trainmate.core.email.TemplateType.PERIODICAL_REPORT_NOTIFICATION;
import static pl.edu.pja.trainmate.core.email.TemplateType.WEEKLY_REPORT_NOTIFICATION;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanQueryService;
import pl.edu.pja.trainmate.core.email.EmailService;

@SpringBootTest
@TestPropertySource(properties = {
    "email-scheduler.periodical-report=0/1 * * * * *",
    "email-scheduler.weekly-report=0/1 * * * * *",
    "spring.datasource.password=postgres",
    "keycloak-admin.secret=secret",
    "email-sender.password=password"

})
class ReportEmailNotificationSchedulerTest {

    @MockBean
    private WorkoutPlanQueryService queryService;

    @MockBean
    private EmailService emailService;

    @Test
    void testSendPeriodicalReportEmailNotificationTriggered() {
        when(queryService.getUserEmailsForEndedWorkoutPlanWithoutReport()).thenReturn(List.of("test@test.com"));

        Awaitility.await().atMost(3, TimeUnit.SECONDS).untilAsserted(() ->
            verify(emailService, atLeastOnce()).sendEmail(anyString(), eq(PERIODICAL_REPORT_NOTIFICATION)));
    }

    @Test
    void testSendWeeklyReportEmailNotificationTriggered() {
        when(queryService.getUserEmailsForEndedWorkoutPlanWithoutReport()).thenReturn(List.of("test@test.com"));

        Awaitility.await().atMost(3, TimeUnit.SECONDS).untilAsserted(() -> {
            verify(emailService, atLeastOnce()).sendEmail(anyString(), eq(WEEKLY_REPORT_NOTIFICATION));
        });
    }
}
