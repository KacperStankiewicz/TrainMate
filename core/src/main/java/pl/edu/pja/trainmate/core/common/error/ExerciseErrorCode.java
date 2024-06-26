package pl.edu.pja.trainmate.core.common.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExerciseErrorCode implements BaseErrorCode {

    COULD_NOT_FIND_EXERCISE("Could not find Exercise entity for id: %s", BAD_REQUEST),
    COULD_NOT_CREATE_EXERCISE("Exercise was not created", BAD_REQUEST),
    INVALID_SEARCH_CRITERIA("Provided exercise search criteria are invalid", BAD_REQUEST),
    NAME_MUST_NOT_BE_NULL("Exercise name must not be null", BAD_REQUEST),
    URL_MUST_NOT_BE_NULL("Exercise video URL must not be null", BAD_REQUEST),
    MUSCLE_INVOLVED_MUST_NOT_BE_NULL("Exercise muscle involved must not be null", BAD_REQUEST),
    MUST_NOT_DELETE_EXERCISE_USED_IN_WORKOUT("Cannot delete exercise used in workouts", BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    ExerciseErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
