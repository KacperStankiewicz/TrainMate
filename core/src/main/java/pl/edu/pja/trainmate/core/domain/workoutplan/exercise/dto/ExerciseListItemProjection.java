package pl.edu.pja.trainmate.core.domain.workoutplan.exercise.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;
import pl.edu.pja.trainmate.core.common.Muscle;

@Value
public class ExerciseListItemProjection {

    Long id;
    String name;
    Muscle muscleInvolved;

    @QueryProjection
    public ExerciseListItemProjection(Long id, String name, Muscle muscleInvolved) {
        this.id = id;
        this.name = name;
        this.muscleInvolved = muscleInvolved;
    }
}
