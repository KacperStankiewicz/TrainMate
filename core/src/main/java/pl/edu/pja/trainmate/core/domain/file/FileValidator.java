package pl.edu.pja.trainmate.core.domain.file;

import static pl.edu.pja.trainmate.core.common.error.FileErrorCode.CANNOT_UPLOAD_MORE_THAN_4_FILES;
import static pl.edu.pja.trainmate.core.common.error.FileErrorCode.FILE_TOO_LARGE;
import static pl.edu.pja.trainmate.core.common.error.FileErrorCode.INCORRECT_EXTENSION_IN_FILE_NAME;
import static pl.edu.pja.trainmate.core.common.error.FileErrorCode.INCORRECT_FORMAT;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.domain.file.dto.FileStorageDto;

@Service
public class FileValidator {

    private static final int UPLOAD_SIZE = 25 * 1024 * 1024;

    public void validateFile(FileStorageDto dto, Long reportFilesCount) {

        if (reportFilesCount > 4) {
            throw new CommonException(CANNOT_UPLOAD_MORE_THAN_4_FILES);
        }

        if (dto != null && isFormatPermitted(dto.getType())) {
            throw new CommonException(INCORRECT_FORMAT);
        }

        if (dto != null && dto.getSize() > UPLOAD_SIZE) {
            throw new CommonException(FILE_TOO_LARGE);
        }

        if (dto != null && isFileExtensionPermitted(dto.getName())) {
            throw new CommonException(INCORRECT_EXTENSION_IN_FILE_NAME);
        }

        if (dto != null && !validateFileEncoding(dto.getContent())) {
            throw new CommonException(CANNOT_UPLOAD_MORE_THAN_4_FILES);
        }
    }

    private boolean isFormatPermitted(String type) {
        return !AttachmentFileTypes.isPermitted(type);
    }

    private boolean isFileExtensionPermitted(String fileName) {
        return !AttachmentFileNameExtensions.isPermitted(fileName);
    }

    private boolean validateFileEncoding(String base64String) {
        return Base64.isBase64(base64String);
    }
}
