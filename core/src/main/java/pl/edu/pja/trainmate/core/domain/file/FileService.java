package pl.edu.pja.trainmate.core.domain.file;

import static pl.edu.pja.trainmate.core.common.error.FileErrorCode.ONLY_OWNER_CAN_DELETE_FILE;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;
import static pl.edu.pja.trainmate.core.domain.file.mapper.FileStorageMapper.toEntity;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.StorageId;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataProvider;
import pl.edu.pja.trainmate.core.domain.file.dto.FileStorageDto;
import pl.edu.pja.trainmate.core.domain.file.mapper.FileStorageMapper;

@Service
@RequiredArgsConstructor
class FileService {

    private final FileStorageRepository repository;
    private final FileValidator validator;
    private final LoggedUserDataProvider loggedUserDataProvider;

    public StorageId addFile(Long reportId, FileStorageDto dto) {
        var reportFilesCount = repository.countFileStorageEntitiesByReportId(reportId);
        validator.validateFile(dto, reportFilesCount);

        var entity = toEntity(dto, reportId);

        return repository.save(entity).getStorageId();
    }

    public List<FileStorageDto> getAllFilesFor(Long reportId) {
        return repository.findAllByReportIdAndCreatedBy(reportId, loggedUserDataProvider.getLoggedUserId())
            .stream()
            .map(FileStorageMapper::toDto)
            .collect(Collectors.toList());
    }

    public void deleteFileBy(StorageId storageId) {
        var userDetails = loggedUserDataProvider.getUserDetails();
        var entity = repository.findByStorageId(storageId);

        if (TRAINED_PERSON.equals(userDetails.getRole()) && !entity.getCreatedBy().getKeycloakId().equals(userDetails.getUserId().getKeycloakId())) {
            throw new CommonException(ONLY_OWNER_CAN_DELETE_FILE);
        }

        repository.delete(entity);
    }
}
