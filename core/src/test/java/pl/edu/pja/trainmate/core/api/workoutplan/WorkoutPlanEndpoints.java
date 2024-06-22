package pl.edu.pja.trainmate.core.api.workoutplan;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class WorkoutPlanEndpoints {

    public static final String GET = "/workout-plan/%s";
    public static final String CREATE = "/workout-plan/create";
    public static final String UPDATE = "/workout-plan/%s/update";
    public static final String DELETE = "/workout-plan/%s/delete";

    public static final String GET_ALL_FOR_USER = "/workout-plan/%s/all";
    public static final String MY_PLANS = "/workout-plan/my-plans";
}
