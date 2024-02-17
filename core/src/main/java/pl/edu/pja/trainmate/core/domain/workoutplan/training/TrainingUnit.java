package pl.edu.pja.trainmate.core.domain.workoutplan.training;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.DayOfWeek;
import javax.persistence.Column;
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

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "training_unit")
@SequenceGenerator(
    name = "training_unit_id_seq_generator",
    sequenceName = "seq_training_unit",
    allocationSize = 1
)
public class TrainingUnit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "training_unit_id_seq_generator")
    private Long id;

    @Column(name = "workout_plan_id")
    private Long workoutPlanId;

    private DayOfWeek dayOfWeek;
    private Long weekNumber;

    @Column(name = "was_completed")
    private Boolean wasCompleted = false;
}
