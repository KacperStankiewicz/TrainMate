package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.annotation.HasRole;
import pl.edu.pja.trainmate.core.common.StorageId;
import pl.edu.pja.trainmate.core.domain.file.FileFacade;
import pl.edu.pja.trainmate.core.domain.file.dto.FileStorageDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileStorageController {

    private final FileFacade fileFacade;

    @Operation(summary = "get all report files")
    @ApiResponse(
        responseCode = "200",
        description = "Got all report files",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = {
        TRAINED_PERSON,
        PERSONAL_TRAINER
    })
    @GetMapping("/{reportId}/get-all")
    public List<FileStorageDto> getAllReportFiles(@PathVariable Long reportId) {
        log.debug("Rest request to GET all files for report with id :{}", reportId);
        var result = fileFacade.getAllFilesFor(reportId);
        log.debug("Successfully GOT all report files");
        return result;
    }

    @Operation(summary = "add file to report")
    @ApiResponse(
        responseCode = "200",
        description = "Added file to report",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = TRAINED_PERSON)
    @PostMapping("/{reportId}/add")
    public StorageId addFileToReport(@PathVariable Long reportId, @RequestBody FileStorageDto dto) {
        log.debug("Rest request to add file to report with id :{}", reportId);
        var result = fileFacade.addFile(reportId, dto);
        log.debug("Successfully added file to report");
        return result;
    }

    @Operation(summary = "delete file")
    @ApiResponse(
        responseCode = "200",
        description = "File deleted",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = {
        TRAINED_PERSON,
        PERSONAL_TRAINER
    })
    @DeleteMapping("/{storageId}/delete")
    public void addFileToReport(@PathVariable UUID storageId) {
        log.debug("Rest request to DELETE file with id :{}", storageId);
        fileFacade.deleteFile(StorageId.valueOf(storageId));
        log.debug("Successfully DELETED file");
    }

}
