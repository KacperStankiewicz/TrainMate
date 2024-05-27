package pl.edu.pja.trainmate.core.api.trainingunit;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class TrainingUnitEndpoints {

    public static final String CREATE = "/training/create";
    public static final String UPDATE = "/training/%s/update";
    public static final String DELETE = "/training/%s/delete";
    public static final String EXERCISE_ITEM_UPDATE = "/training/exercise/%s/update";
    public static final String EXERCISE_ITEM_DELETE = "/training/exercise/%s/delete";
}
