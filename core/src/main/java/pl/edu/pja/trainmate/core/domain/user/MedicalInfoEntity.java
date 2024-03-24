package pl.edu.pja.trainmate.core.domain.user;

import static javax.persistence.GenerationType.SEQUENCE;

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

@Table(name = "medical_info", schema = "public")
@Entity
@SequenceGenerator(
    name = "medical_info_id_seq_generator",
    sequenceName = "seq_medical_info",
    allocationSize = 1
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MedicalInfoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "medical_info_id_seq_generator")
    private Long id;

    private Long userId;

    @Column(name = "injuries")
    private String injuries; //todo : serializacja i deserializacja z/na JSON

}
