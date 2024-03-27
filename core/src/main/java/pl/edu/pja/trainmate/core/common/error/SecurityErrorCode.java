package pl.edu.pja.trainmate.core.common.error;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SecurityErrorCode implements BaseErrorCode {

    NO_ACCESS_TO_THE_RESOURCE("No access to the resource", FORBIDDEN);

    private final String message;
    private final HttpStatus httpStatus;

    SecurityErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.httpStatus = status;
    }
}
