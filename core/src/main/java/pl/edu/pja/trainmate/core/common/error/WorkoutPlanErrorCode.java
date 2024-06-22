package pl.edu.pja.trainmate.core.common.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum WorkoutPlanErrorCode implements BaseErrorCode {

    COULD_NOT_CREATE_WORKOUT_PLAN("Workout plan was not created", BAD_REQUEST),
    USER_DOES_NOT_HAVE_ACTIVE_WORKOUT_PLAN("User does not have workout plan for current date", BAD_REQUEST),
    USER_DOES_NOT_HAVE_ANY_WORKOUT_PLAN("User does not have any workout plan", BAD_REQUEST),
    WORKOUT_PLAN_MUST_HAVE_START_DATE("Workout plan must have start date", BAD_REQUEST),
    WORKOUT_PLAN_MUST_HAVE_DURATION("Workout plan must have duration", BAD_REQUEST),
    MUST_NOT_CHANGE_ACTIVE_WORKOUT_PLAN("Must not change active workout plan", BAD_REQUEST),
    START_DATE_MUST_NOT_BE_BEFORE_TODAY("Workout plan start date must not be before today", BAD_REQUEST),
    WORKOUT_PLAN_HAS_EXERCISES_THAT_WILL_BE_DELETED("Workout plan has exercises that will be deleted due to duration change", BAD_REQUEST),
    WORKOUT_PLAN_START_DATE_OVERLAPS_WITH_OTHER_WORKOUT_PLAN("Must not create workout plan that overlaps with other workout plans", BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    WorkoutPlanErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
