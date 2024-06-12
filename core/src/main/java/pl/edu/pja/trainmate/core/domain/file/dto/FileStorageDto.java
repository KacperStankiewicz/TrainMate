package pl.edu.pja.trainmate.core.domain.file.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.common.StorageId;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
@Value
public class FileStorageDto {

    StorageId storageId;
    String content;
    String name;
    String type;
    Long size;
}
