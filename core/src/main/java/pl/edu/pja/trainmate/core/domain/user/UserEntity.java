package pl.edu.pja.trainmate.core.domain.user;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;
import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.MENTEE_ACCOUNT_IS_ALREADY_ACTIVE;
import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.MENTEE_ACCOUNT_IS_ALREADY_INACTIVE;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.config.security.RoleType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user", schema = "public")
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

    @Enumerated(STRING)
    @Column(name = "role")
    private RoleType role;

    private boolean active = true;

    public void updatePersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public void setActive(boolean active) {
        if (this.active == active) {
            throw new CommonException(active ? MENTEE_ACCOUNT_IS_ALREADY_ACTIVE : MENTEE_ACCOUNT_IS_ALREADY_INACTIVE);
        }
        this.active = active;
    }
}