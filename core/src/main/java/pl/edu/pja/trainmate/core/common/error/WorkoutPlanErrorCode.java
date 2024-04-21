package pl.edu.pja.trainmate.core.common.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum WorkoutPlanErrorCode implements BaseErrorCode {

    COULD_NOT_CREATE_WORKOUT_PLAN("Workout plan was not created", BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    WorkoutPlanErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
