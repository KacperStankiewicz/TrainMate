package pl.edu.pja.trainmate.core.api.sampledata;

import static pl.edu.pja.trainmate.core.ControllerSpecification.EXISTING_USER_ID;

import java.util.Base64;
import java.util.UUID;
import pl.edu.pja.trainmate.core.common.StorageId;
import pl.edu.pja.trainmate.core.domain.file.FileStorageEntity;
import pl.edu.pja.trainmate.core.domain.file.dto.FileStorageDto;

public class FileStorageSampleData {

    public static FileStorageDto.FileStorageDtoBuilder getSampleFileStorageDtoBuilder() {
        return FileStorageDto.builder()
            .storageId(StorageId.valueOf(UUID.randomUUID()))
            .content(Base64.getEncoder().encodeToString("test".getBytes()))
            .name("name.png")
            .type("image/png")
            .size(10L);
    }

    public static FileStorageEntity.FileStorageEntityBuilder getSampleFileStorageEntityBuilder() {
        var entity = FileStorageEntity.builder()
            .storageId(StorageId.valueOf(UUID.randomUUID()))
            .fileContent(Base64.getEncoder().encodeToString("test".getBytes()))
            .fileName("name.png")
            .fileType("image/png")
            .fileSize(10L)
            .reportId(1L)
            .build();

        entity.setCreatedBy(EXISTING_USER_ID);
        return entity.toBuilder();
    }
}
