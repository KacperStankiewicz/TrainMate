package pl.edu.pja.trainmate.core.domain.dietplan.dish;

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

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dish")
@SequenceGenerator(
    name = "dish_id_seq_generator",
    sequenceName = "seq_dish",
    allocationSize = 1
)
public class DishEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "dish_id_seq_generator")
    private Long id;

    private Long mealId;
    private String instruction;
    private String instructionVideoUrl;
}
