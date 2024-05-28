package pl.edu.pja.trainmate.core.domain.exercise.dto;

import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.common.Muscle;

@Value
@NoArgsConstructor(force = true, access = PRIVATE)
public class ExerciseProjection {

    Long id;
    String name;
    String description;
    String url;
    Muscle muscleInvolved;

    @QueryProjection
    public ExerciseProjection(Long id, String name, String description, String url, Muscle muscleInvolved) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.muscleInvolved = muscleInvolved;
    }
}
