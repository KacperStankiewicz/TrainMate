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

@Table(name = "user", schema = "public")
@Entity
@SequenceGenerator(
    name = "user_id_seq_generator",
    sequenceName = "seq_user",
    allocationSize = 1
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "user_id_seq_generator")
    private Long id;

    @Column(name = "name")
    private String name;
}
