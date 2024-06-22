package pl.edu.pja.trainmate.core.domain.file;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.StorageId;
import pl.edu.pja.trainmate.core.domain.file.dto.FileStorageDto;

@Service
@RequiredArgsConstructor
public class FileFacade {

    private final FileService service;

    public void addFiles(Long reportId, List<FileStorageDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return;
        }
        dtos.forEach(it -> addFile(reportId, it));
    }

    public StorageId addFile(Long reportId, FileStorageDto dto) {
        return service.addFile(reportId, dto);
    }

    public void deleteAllFilesByReportId(Long reportId) {
        var files = getAllFilesFor(reportId);

        files.forEach(it -> deleteFile(it.getStorageId()));
    }

    public List<FileStorageDto> getAllFilesFor(Long reportId) {
        return service.getAllFilesFor(reportId);
    }

    public void deleteFile(StorageId storageId) {
        service.deleteFileBy(storageId);
    }
}
