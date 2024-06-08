package pl.edu.pja.trainmate.core.scheduler;

import static pl.edu.pja.trainmate.core.email.TemplateType.PERIODICAL_REPORT_NOTIFICATION;
import static pl.edu.pja.trainmate.core.email.TemplateType.WEEKLY_REPORT_NOTIFICATION;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanQueryService;
import pl.edu.pja.trainmate.core.email.EmailService;

@Component
@RequiredArgsConstructor
public class ReportEmailNotificationScheduler {

    private final WorkoutPlanQueryService queryService;
    private final EmailService emailService;

    @Scheduled(cron = "${email-scheduler.periodical-report}")
    public void sendPeriodicalReportEmailNotification() {
        var emails = queryService.getUserEmailsForEndedWorkoutPlanWithoutReport();

        emails.forEach(it -> emailService.sendEmail(it, PERIODICAL_REPORT_NOTIFICATION));
    }

    @Scheduled(cron = "${email-scheduler.weekly-report}")
    public void sendWeeklyReportEmailNotification() {
        // todo: zmienic linie nizej
        var emails = queryService.getUserEmailsForEndedWorkoutPlanWithoutReport();

        emails.forEach(it -> emailService.sendEmail(it, WEEKLY_REPORT_NOTIFICATION));
    }
}
