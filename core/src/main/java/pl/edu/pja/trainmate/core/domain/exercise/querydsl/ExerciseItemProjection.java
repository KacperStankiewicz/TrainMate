package pl.edu.pja.trainmate.core.domain.exercise.querydsl;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.common.Muscle;

@NoArgsConstructor(access = PRIVATE, force = true)
@Value
public class ExerciseItemProjection {

    Long id;
    Integer repetitions;
    String tempo;
    Integer weight;
    Integer rir;
    Integer sets;
    Muscle muscleInvolved;
    String name;
    String description;
    String url;
    boolean reported;
    @JsonIgnore
    Long trainingUnitId;

    @QueryProjection
    public ExerciseItemProjection(Long id, Integer repetitions, String tempo, Integer weight, Integer rir, Integer sets,
        Muscle muscleInvolved, String name, String description, String url, boolean reported, Long trainingUnitId) {
        this.id = id;
        this.repetitions = repetitions;
        this.tempo = tempo;
        this.weight = weight;
        this.rir = rir;
        this.sets = sets;
        this.muscleInvolved = muscleInvolved;
        this.name = name;
        this.description = description;
        this.url = url;
        this.reported = reported;
        this.trainingUnitId = trainingUnitId;
    }
}
