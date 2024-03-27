package pl.edu.pja.trainmate.core.common.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCode implements BaseErrorCode {

    ENTITY_NOT_FOUND("ENTITY_NOT_FOUND", BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    CommonErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.httpStatus = status;
    }
}