package pl.edu.pja.trainmate.core.domain.workoutplan.exercise.dto;

import pl.edu.pja.trainmate.core.common.Muscle;

public interface ExerciseData {

    default Long getId() {
        return null;
    }

    String getName();

    String getDescription();

    String getUrl();

    Muscle getMuscleInvolved();
}
