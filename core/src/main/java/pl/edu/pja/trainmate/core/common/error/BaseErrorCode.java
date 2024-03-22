package pl.edu.pja.trainmate.core.common.error;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

    String name();

    default String getCode() {
        return name();
    }

    String getMessage();

    default String format(Object... args) {
        return String.format(getMessage(), args);
    }

    HttpStatus getHttpStatus();
}
