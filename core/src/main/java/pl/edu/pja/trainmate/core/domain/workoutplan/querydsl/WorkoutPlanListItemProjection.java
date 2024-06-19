package pl.edu.pja.trainmate.core.domain.workoutplan.querydsl;

import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.common.DateRange;
import pl.edu.pja.trainmate.core.domain.report.querydsl.PeriodicalReportBasicInfoProjection;

@Value
@NoArgsConstructor(access = PRIVATE, force = true)
public class WorkoutPlanListItemProjection {

    Long id;
    String name;
    DateRange dateRange;
    PeriodicalReportBasicInfoProjection report;

    @QueryProjection
    public WorkoutPlanListItemProjection(Long id, String name, DateRange dateRange, PeriodicalReportBasicInfoProjection report) {
        this.id = id;
        this.name = name;
        this.dateRange = dateRange;
        this.report = report;
    }
}
