package pl.edu.pja.trainmate.core.domain.workoutplan;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edu.pja.trainmate.core.common.BaseEntity;
import pl.edu.pja.trainmate.core.common.DateRange;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanDto;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workout_plan")
@SequenceGenerator(
    name = "workout_plan_id_seq_generator",
    sequenceName = "seq_workout_plan",
    allocationSize = 1
)
public class WorkoutPlanEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "workout_plan_id_seq_generator")
    private Long id;

    private String name;

    @AttributeOverride(name = "keycloakId", column = @Column(name = "user_id"))
    private UserId userId;
    private String category;

    @Embedded
    @AttributeOverride(name = "from", column = @Column(name = "workout_plan_start_date"))
    @AttributeOverride(name = "to", column = @Column(name = "workout_plan_end_date"))
    private DateRange dateRange;

    public void update(WorkoutPlanDto workoutPlanDto) {
        this.name = workoutPlanDto.getName();
        this.category = workoutPlanDto.getCategory();
    }
}
