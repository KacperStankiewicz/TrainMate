package pl.edu.pja.trainmate.core.domain.training;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.DayOfWeek;
import javax.persistence.Column;
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
import org.springframework.util.DigestUtils;
import pl.edu.pja.trainmate.core.common.BaseEntity;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitDto;

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
public class TrainingUnitEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "training_unit_id_seq_generator")
    private Long id;

    @Column(name = "workout_plan_id")
    private Long workoutPlanId;

    @Enumerated(STRING)
    private DayOfWeek dayOfWeek;
    private Long weekNumber;

    @Column(name = "completed")
    private Boolean completed = false; //todo: completed

    @JsonIgnore
    @Column(name = "unique_hash", unique = true, nullable = false)
    private String uniqueHash;

    //todo: dodac training duration

    public void markAsCompleted() {
        this.completed = true;
    }

    public void markAsNotCompleted() {
        this.completed = false;
    }

    public void update(TrainingUnitDto trainingUnitDto) {
        this.dayOfWeek = trainingUnitDto.getDayOfWeek();
        this.weekNumber = trainingUnitDto.getWeekNumber();
        calculateHash();
    }

    public void calculateHash() {
        var seed = String.join(";", this.workoutPlanId.toString(), this.weekNumber.toString(), this.dayOfWeek.name());

        this.uniqueHash = DigestUtils.md5DigestAsHex(seed.getBytes(UTF_8));
    }
}
