package pl.edu.pja.trainmate.core.domain.user;

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
import pl.edu.pja.trainmate.core.common.UserId;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@SequenceGenerator(
    name = "user_id_seq_generator",
    sequenceName = "seq_user",
    allocationSize = 1
)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "user_id_seq_generator")
    private Long id;

    @Embedded
    private PersonalInfo personalInfo;

    @Embedded
    private UserId userId;
}