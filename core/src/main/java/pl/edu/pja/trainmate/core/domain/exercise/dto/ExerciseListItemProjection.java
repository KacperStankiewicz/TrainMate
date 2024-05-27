package pl.edu.pja.trainmate.core.domain.exercise.dto;

import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.common.Muscle;

@Value
@NoArgsConstructor(force = true, access = PRIVATE)
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
