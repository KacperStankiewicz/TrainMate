package pl.edu.pja.trainmate.core.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

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
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "user_id_seq_generator")
    private Long id;

    @Column(name = "name")
    private String name;

}
