package pl.edu.pja.trainmate.core.email;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Entity
@Builder
@FieldNameConstants
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "email_template"
)
@SequenceGenerator(
    name = "email_template_id_seq_generator",
    sequenceName = "seq_email_template",
    allocationSize = 1
)
public class EmailTemplateEntity {

    @Id
    @GeneratedValue(
        strategy = SEQUENCE,
        generator = "email_template_id_seq_generator"
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    TemplateType type;

    @Setter
    @Column(columnDefinition = "clob")
    private String content;
    private String subject;
}