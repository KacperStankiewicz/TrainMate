package pl.edu.pja.trainmate.core.domain.workoutplan.exercise;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edu.pja.trainmate.core.common.BaseEntity;
import pl.edu.pja.trainmate.core.common.Muscle;
import pl.edu.pja.trainmate.core.domain.workoutplan.exercise.dto.ExerciseDto;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exercise")
@SequenceGenerator(
    name = "exercise_id_seq_generator",
    sequenceName = "seq_exercise",
    allocationSize = 1
)
public class ExerciseEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "exercise_id_seq_generator")
    private Long id;

    private String name;
    private String description;
    private String url;

    @Enumerated(STRING)
    private Muscle muscleInvolved;

    public void update(ExerciseDto dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.url = dto.getUrl();
        this.muscleInvolved = dto.getMuscleInvolved();
    }
}
