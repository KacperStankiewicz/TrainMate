package pl.edu.pja.trainmate.core.domain.workoutplan.querydsl;

import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import java.time.temporal.ChronoUnit;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.common.DateRange;

@NoArgsConstructor(access = PRIVATE, force = true)
@Value
public class WorkoutPlanProjection {

    Long id;
    Long version;
    String name;
    String category;
    DateRange dateRange;
    Long duration;

    @QueryProjection
    public WorkoutPlanProjection(Long id, Long version, String name, String category, DateRange dateRange) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.category = category;
        this.dateRange = dateRange;
        this.duration = ChronoUnit.WEEKS.between(dateRange.getFrom(), dateRange.getTo());
    }
}
