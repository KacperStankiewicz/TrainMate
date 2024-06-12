package pl.edu.pja.trainmate.core.domain.file;

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
import lombok.experimental.FieldNameConstants;
import pl.edu.pja.trainmate.core.common.BaseEntity;
import pl.edu.pja.trainmate.core.common.StorageId;
import pl.edu.pja.trainmate.core.common.UserId;

@Entity
@Builder(toBuilder = true)
@FieldNameConstants
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "file_storage"
)
@SequenceGenerator(
    name = "file_storage_id_seq_generator",
    sequenceName = "seq_file_storage",
    allocationSize = 1
)
public class FileStorageEntity extends BaseEntity {

    @Id
    @GeneratedValue(
        strategy = SEQUENCE,
        generator = "file_storage_id_seq_generator"
    )
    private Long id;

    private Long reportId;

    @Embedded
    private StorageId storageId;

    private String fileName;

    @Column(columnDefinition = "TEXT")
    private String fileContent;

    private String fileType;
    private Long fileSize;
}
