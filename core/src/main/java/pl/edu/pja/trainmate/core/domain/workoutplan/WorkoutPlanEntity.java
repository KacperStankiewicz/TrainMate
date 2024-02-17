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
import pl.edu.pja.trainmate.core.common.UserId;

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
public class WorkoutPlanEntity extends BaseEntity { //todo: ja bym połączył te oncje i DietPlanEntity w jedno. Żeby był po porstu PlanEntity z enumem w srodku. a moze w ogole jest niepotrzebna?

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "workout_plan_id_seq_generator")
    private Long id;

    private String name;
    private UserId userId;
    private String category;
}
