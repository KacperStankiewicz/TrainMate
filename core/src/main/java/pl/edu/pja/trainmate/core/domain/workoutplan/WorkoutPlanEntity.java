package pl.edu.pja.trainmate.core.domain.workoutplan;

import static javax.persistence.GenerationType.SEQUENCE;

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

    private Long userId;
    private String category;

    public void update(WorkoutPlanDto workoutPlanDto) {
        this.name = workoutPlanDto.getName();
        this.userId = workoutPlanDto.getUserId();
        this.category = workoutPlanDto.getCategory();
    }
}
