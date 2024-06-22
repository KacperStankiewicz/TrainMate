package pl.edu.pja.trainmate.core.common.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FileErrorCode implements BaseErrorCode {

    CANNOT_UPLOAD_MORE_THAN_4_FILES("Cannot upload more than 4 files for report", BAD_REQUEST),
    INCORRECT_FORMAT("Incorrect format", BAD_REQUEST),
    FILE_TOO_LARGE("File too large", BAD_REQUEST),
    FILE_EMPTY("File empty", BAD_REQUEST),
    FILE_MUST_BE_ENCODED("File must be encoded in base64", BAD_REQUEST),
    INCORRECT_EXTENSION_IN_FILE_NAME("Incorrect extension in file name", BAD_REQUEST),
    ONLY_OWNER_CAN_DELETE_FILE("Only owner of the file can delete it", BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    FileErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
