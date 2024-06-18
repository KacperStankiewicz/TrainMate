package pl.edu.pja.trainmate.core.api.report;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class ReportEndpoints {

    public static final String WORKOUT_PLAN_REPORT = "/workout-plan/%s/report";
    public static final String WORKOUT_PLAN_REPORT_REVIEW = "/workout-plan/report/%s/review";
    public static final String EXERCISE_REPORT = "/exercise/%s/report";
}
