package pl.edu.pja.trainmate.core.common.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MenteeErrorCode implements BaseErrorCode {

    COULD_NOT_CREATE_MENTEE("Mentee was not created", BAD_REQUEST),
    COULD_NOT_FIND_MENTEE("Could not find mentee with id: %s", BAD_REQUEST),
    MENTEE_WITH_PROVIDED_EMAIL_ALREADY_EXISTS("Mentee with email: %s already exists", BAD_REQUEST),
    MENTEE_ACCOUNT_IS_ALREADY_ACTIVE("Mentee account is already active", BAD_REQUEST),
    MENTEE_ACCOUNT_IS_ALREADY_INACTIVE("Mentee account is already inactive", BAD_REQUEST),
    EMAIL_MUST_NOT_BE_NULL("Email address must not be null", BAD_REQUEST),
    FIRSTNAME_MUST_NOT_BE_NULL("Firstname address must not be null", BAD_REQUEST),
    LASTNAME_MUST_NOT_BE_NULL("Lastname address must not be null", BAD_REQUEST),
    INVALID_DATE_OF_BIRTH("Invalid  date of birth", BAD_REQUEST),
    INVALID_PHONE_NUMBER("Invalid phone number", BAD_REQUEST),
    GENDER_MUST_NOT_BE_NULL("Gender must not be null", BAD_REQUEST),
    INVALID_HEIGHT("Invalid height provided", BAD_REQUEST);
    private final String message;
    private final HttpStatus httpStatus;

    MenteeErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
