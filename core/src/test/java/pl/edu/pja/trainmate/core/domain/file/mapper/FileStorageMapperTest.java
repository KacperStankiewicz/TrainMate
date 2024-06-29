package pl.edu.pja.trainmate.core.domain.file.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static pl.edu.pja.trainmate.core.domain.file.mapper.FileStorageMapper.toDto;
import static pl.edu.pja.trainmate.core.domain.file.mapper.FileStorageMapper.toEntity;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import pl.edu.pja.trainmate.core.common.StorageId;
import pl.edu.pja.trainmate.core.domain.file.FileStorageEntity;
import pl.edu.pja.trainmate.core.domain.file.dto.FileStorageDto;

class FileStorageMapperTest {

    @Test
    void shouldMapToNullWithNullEntity() {
        FileStorageEntity entity = null;

        var dto = toDto(entity);

        assertNull(dto);
    }

    @Test
    void shouldMapToDtoWithValidEntity() {
        var entity = FileStorageEntity.builder()
            .storageId(StorageId.valueOf(UUID.randomUUID()))
            .fileContent("content")
            .fileName("name")
            .fileType("type")
            .fileSize(123L)
            .build();

        var dto = toDto(entity);

        assertEquals(entity.getStorageId(), dto.getStorageId());
        assertEquals(entity.getFileContent(), dto.getContent());
        assertEquals(entity.getFileName(), dto.getName());
        assertEquals(entity.getFileType(), dto.getType());
        assertEquals(entity.getFileSize(), dto.getSize());
    }

    @Test
    void shouldMaptoEntityWithValidDto() {
        var dto = FileStorageDto.builder()
            .storageId(StorageId.valueOf(UUID.randomUUID()))
            .content("content")
            .name("name.png")
            .type("type")
            .size(123L)
            .build();

        var entity = toEntity(dto, 1L);

        assertNotEquals(dto.getStorageId(), entity.getStorageId());
        assertEquals(dto.getContent(), entity.getFileContent());
        assertNotEquals(dto.getName(), entity.getFileName());
        assertEquals(dto.getType(), entity.getFileType());
        assertEquals(dto.getSize(), entity.getFileSize());
        assertEquals(1L, entity.getReportId());
    }
}