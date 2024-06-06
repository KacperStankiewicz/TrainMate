package pl.edu.pja.trainmate.core.domain.workoutplan.querydsl;

import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.common.DateRange;

@NoArgsConstructor(access = PRIVATE, force = true)
@Value
public class WorkoutPlanProjection {

    Long id;
    String name;
    String category;
    DateRange dateRange;

    @QueryProjection
    public WorkoutPlanProjection(Long id, String name, String category, DateRange dateRange) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.dateRange = dateRange;
    }
}
