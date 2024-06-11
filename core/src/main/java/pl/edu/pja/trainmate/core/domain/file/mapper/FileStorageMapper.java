package pl.edu.pja.trainmate.core.domain.file.mapper;

import java.util.UUID;
import pl.edu.pja.trainmate.core.common.StorageId;
import pl.edu.pja.trainmate.core.domain.file.FileStorageEntity;
import pl.edu.pja.trainmate.core.domain.file.dto.FileStorageDto;

public final class FileStorageMapper {

    public static FileStorageDto toDto(FileStorageEntity entity) {
        if (entity == null) {
            return null;
        }

        return FileStorageDto.builder()
            .storageId(entity.getStorageId())
            .content(entity.getFileContent())
            .name(entity.getFileName())
            .type(entity.getFileType())
            .size(entity.getFileSize())
            .build();
    }

    public static FileStorageEntity toEntity(FileStorageDto dto, Long reportId) {
        return FileStorageEntity.builder()
            .storageId(StorageId.valueOf(UUID.randomUUID()))
            .fileContent(dto.getContent())
            .fileName(dto.getName())
            .fileType(dto.getType())
            .fileSize(dto.getSize())
            .reportId(reportId)
            .build();

    }
}
