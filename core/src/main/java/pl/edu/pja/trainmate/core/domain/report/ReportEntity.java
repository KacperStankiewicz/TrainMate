package pl.edu.pja.trainmate.core.domain.report;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.AttributeOverride;
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
import pl.edu.pja.trainmate.core.common.UserId;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report")
@SequenceGenerator(
    name = "report_id_seq_generator",
    sequenceName = "seq_report",
    allocationSize = 1
)
public class ReportEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "report_id_seq_generator")
    Long id;

    @AttributeOverride(name = "keycloakId", column = @Column(name = "user_id"))
    UserId userId;
    Long workoutPlanId;
    Double weight;
    Integer bodyFat;

    @Embedded
    BodyCircumferences circumferences;

    @Builder.Default
    boolean initial = false;
    @Builder.Default
    boolean reviewed = false;

    public void markAsReviewed() {
        this.reviewed = true;
    }
}
