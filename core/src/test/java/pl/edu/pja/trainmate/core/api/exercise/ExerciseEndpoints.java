package pl.edu.pja.trainmate.core.api.exercise;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class ExerciseEndpoints {

    public static final String SEARCH = "/exercise/search";
    public static final String UPDATE = "/exercise/%s";
    public static final String DELETE = "/exercise/%s";
    public static final String CREATE = "/exercise/create";
    public static final String GET = "/exercise/%s";

}
