package pl.edu.pja.trainmate.core.domain.exercise;

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
@Table(name = "exercise_item")
@SequenceGenerator(
    name = "exercise_item_id_seq_generator",
    sequenceName = "seq_exercise_item",
    allocationSize = 1
)
public class ExerciseItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "exercise_item_id_seq_generator")
    private Long id;

    @Column(name = "exercise_id")
    private Long exerciseId;

    @Column(name = "training_unit_id")
    private Long trainingUnitId;

    @Embedded
    private Volume volume;
}


