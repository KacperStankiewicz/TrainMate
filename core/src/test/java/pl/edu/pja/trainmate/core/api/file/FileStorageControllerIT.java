package pl.edu.pja.trainmate.core.api.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static pl.edu.pja.trainmate.core.api.file.FileStorageEndpoints.ADD;
import static pl.edu.pja.trainmate.core.api.file.FileStorageEndpoints.DELETE;
import static pl.edu.pja.trainmate.core.api.sampledata.FileStorageSampleData.getSampleFileStorageDtoBuilder;
import static pl.edu.pja.trainmate.core.api.sampledata.FileStorageSampleData.getSampleFileStorageEntityBuilder;
import static pl.edu.pja.trainmate.core.config.security.RoleType.MENTEE;
import static pl.edu.pja.trainmate.core.testutils.ResponseConverter.castResponseTo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.StorageId;
import pl.edu.pja.trainmate.core.domain.file.FileStorageRepository;

@SpringBootTest
@AutoConfigureMockMvc
class FileStorageControllerIT extends ControllerSpecification {

    private static final Long ID = 1L;

    @Autowired
    private FileStorageRepository repository;

    @AfterEach
    private void clean() {
        repository.deleteAll();
    }

    @Test
    void shouldAddFileToReport() {
        //given
        userWithRole(MENTEE);
        var dto = getSampleFileStorageDtoBuilder().build();

        //when
        var response = performPost(String.format(ADD, ID), dto).getResponse();

        //then
        assertEquals(200, response.getStatus());

        //and
        var responseBody = castResponseTo(response, StorageId.class);
        var entity = repository.findByStorageId(responseBody);

        assertEquals(dto.getSize(), entity.getFileSize());
        assertEquals(dto.getType(), entity.getFileType());
        assertNotEquals(dto.getName(), entity.getFileName());
        assertEquals(dto.getContent(), entity.getFileContent());
    }

    @Test
    void shouldDeleteFile() {
        //given
        userWithRole(MENTEE);
        var entity = repository.save(getSampleFileStorageEntityBuilder().build());

        //when
        var response = performDelete(String.format(DELETE, entity.getStorageId().getValue().toString())).getResponse();

        //then
        assertEquals(200, response.getStatus());

        //and
        assertEquals(0, repository.findAll().size());
    }
}
