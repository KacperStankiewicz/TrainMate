package pl.edu.pja.trainmate.core.common.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MenteeErrorCode implements BaseErrorCode {

    COULD_NOT_CREATE_MENTEE("Mentee was not created", BAD_REQUEST);


    private final String message;
    private final HttpStatus httpStatus;

    MenteeErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
