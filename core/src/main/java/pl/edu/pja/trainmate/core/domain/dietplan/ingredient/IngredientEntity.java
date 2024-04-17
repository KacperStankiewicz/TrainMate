package pl.edu.pja.trainmate.core.domain.dietplan.ingredient;

import static javax.persistence.GenerationType.SEQUENCE;

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

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ingredient")
@SequenceGenerator(
    name = "ingredient_id_seq_generator",
    sequenceName = "seq_ingredient",
    allocationSize = 1
)
public class IngredientEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "ingredient_id_seq_generator")
    private Long id;

    @Embedded
    private Macros macros;
    private String name;
    private String category; //todo: pewnie trzeba to jako enum zrobic -> mieso, nabial etc.
    @Column(name = "photo", columnDefinition = "clob")
    private String photo; //todo: to pewnie nie bedzie string
}
