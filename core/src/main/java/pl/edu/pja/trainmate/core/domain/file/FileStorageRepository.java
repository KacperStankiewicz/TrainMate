package pl.edu.pja.trainmate.core.domain.file;

import java.util.List;
import org.springframework.stereotype.Repository;
import pl.edu.pja.trainmate.core.common.BaseRepository;
import pl.edu.pja.trainmate.core.common.StorageId;
import pl.edu.pja.trainmate.core.common.UserId;

@Repository
public interface FileStorageRepository extends BaseRepository<FileStorageEntity> {


    Long countFileStorageEntitiesByReportId(Long reportId);

    List<FileStorageEntity> findAllByReportIdAndCreatedBy(Long reportId, UserId ownerId);

    List<FileStorageEntity> findAllByReportId(Long reportId);

    FileStorageEntity findByStorageId(StorageId storageId);
}
