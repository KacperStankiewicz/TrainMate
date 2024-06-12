package pl.edu.pja.trainmate.core.scheduler;

import static pl.edu.pja.trainmate.core.domain.email.TemplateType.PERIODICAL_REPORT_NOTIFICATION;
import static pl.edu.pja.trainmate.core.domain.email.TemplateType.WEEKLY_REPORT_NOTIFICATION;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.pja.trainmate.core.domain.email.EmailService;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanQueryService;

@Component
@RequiredArgsConstructor
public class ReportEmailNotificationScheduler {

    private final WorkoutPlanQueryService queryService;
    private final EmailService emailService;

    @Scheduled(cron = "${email-scheduler.periodical-report}")
    public void sendPeriodicalReportEmailNotification() {
        var emails = queryService.getUsersEmailsForEndedWorkoutPlanWithoutReport();

        emails.forEach(it -> emailService.sendEmail(it, PERIODICAL_REPORT_NOTIFICATION));
    }

    @Scheduled(cron = "${email-scheduler.weekly-report}")
    public void sendWeeklyReportEmailNotification() {
        var emails = queryService.getUsersEmailsWithActiveWorkoutPlan();

        emails.forEach(it -> emailService.sendEmail(it, WEEKLY_REPORT_NOTIFICATION));
    }
}
