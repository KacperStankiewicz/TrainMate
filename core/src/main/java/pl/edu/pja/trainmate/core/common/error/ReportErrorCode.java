package pl.edu.pja.trainmate.core.common.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ReportErrorCode implements BaseErrorCode {

    COULD_NOT_CREATE_REPORT("Could not create report", BAD_REQUEST),
    EXERCISE_WAS_ALREADY_REPORTED("Can not create new report for exercise that was already reported", BAD_REQUEST),
    EXERCISE_WAS_NOT_REPORTED("Can not review report if exercise is not reported", BAD_REQUEST),
    WORKOUT_PLAN_WAS_ALREADY_REPORTED("Report for workout plan with id {} already exists", BAD_REQUEST),
    INITIAL_REPORT_ALREADY_EXISTS("Initial report already exists", BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    ReportErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}