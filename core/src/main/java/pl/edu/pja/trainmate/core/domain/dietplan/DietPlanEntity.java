package pl.edu.pja.trainmate.core.domain.dietplan;

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
import pl.edu.pja.trainmate.core.common.DateRange;
import pl.edu.pja.trainmate.core.common.UserId;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "diet_plan")
@SequenceGenerator(
    name = "diet_plan_id_seq_generator",
    sequenceName = "seq_diet_plan",
    allocationSize = 1
)
public class DietPlanEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "diet_plan_id_seq_generator")
    private Long id;

    private String name;
    private UserId userId;
    private String category;
    private DateRange period; //todo: pomyslec trzeba nad lepsza nazwa bo samo period malo mowi chyba
}
