package pl.edu.pja.trainmate.core.domain.dietplan.meal;

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
import pl.edu.pja.trainmate.core.common.MealTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "meal")
@SequenceGenerator(
    name = "meal_id_seq_generator",
    sequenceName = "seq_meal",
    allocationSize = 1
)
public class MealEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "meal_id_seq_generator")
    private Long id;

    @Column(name = "diet_plan_id")
    private Long dietPlanId;

    private Long weekNumber;
    private DayOfWeek dayOfWeek;
    private MealTime mealTime;
}
