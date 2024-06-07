package pl.edu.pja.trainmate.core.common.error;

import org.springframework.http.HttpStatus;

public enum EmailErrorCode implements BaseErrorCode {

    CANNOT_CONNECT_TO_HOST("Cannot connect to host: %s"),
    UNEXPECTED_ERROR_WHILE_SENDING_EMAIL("Unexpected error while sending email, reason: %s");

    private final String message;
    private final HttpStatus httpStatus;

    EmailErrorCode(String message) {
        this.message = message;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
