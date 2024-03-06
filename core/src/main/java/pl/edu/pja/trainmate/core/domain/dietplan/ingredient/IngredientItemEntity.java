package pl.edu.pja.trainmate.core.domain.dietplan.ingredient;

import static javax.persistence.GenerationType.SEQUENCE;

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
import pl.edu.pja.trainmate.core.common.IngredientAmount;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ingredient_item")
@SequenceGenerator(
    name = "ingredient_item_id_seq_generator",
    sequenceName = "seq_ingredient_item",
    allocationSize = 1
)
public class IngredientItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "ingredient_item_id_seq_generator")
    private Long id;
    private Long dishId;
    private Long ingredientId;

    @Embedded
    private IngredientAmount amount;
}
