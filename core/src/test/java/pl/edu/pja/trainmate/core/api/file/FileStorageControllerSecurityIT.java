package pl.edu.pja.trainmate.core.api.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static pl.edu.pja.trainmate.core.api.file.FileStorageEndpoints.ADD;
import static pl.edu.pja.trainmate.core.api.sampledata.FileStorageSampleData.getSampleFileStorageDtoBuilder;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.exception.SecurityException;

@SpringBootTest
@AutoConfigureMockMvc
class FileStorageControllerSecurityIT extends ControllerSpecification {

    private static final Long ID = 1L;

    @Test
    void shouldNotAllowAccessForPersonalTrainerWhenAddingFileToReport() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var criteria = getSampleFileStorageDtoBuilder().build();

        //when
        var response = performPost(String.format(ADD, ID), criteria);

        //then
        var exception = (SecurityException) response.getResolvedException();
        assertEquals(FORBIDDEN, exception.getStatus());
    }
}