package pl.edu.pja.trainmate.core.domain.training.querydsl;


import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.ExerciseItemProjection;

@NoArgsConstructor(access = PRIVATE, force = true)
@Value
public class TrainingUnitProjection {

    Long id;
    Long version;
    DayOfWeek dayOfWeek;
    Long weekNumber;
    List<ExerciseItemProjection> exercises = new ArrayList<>();

    @QueryProjection
    public TrainingUnitProjection(Long id, Long version, DayOfWeek dayOfWeek, Long weekNumber) {
        this.id = id;
        this.version = version;
        this.dayOfWeek = dayOfWeek;
        this.weekNumber = weekNumber;
    }

    public void addExercises(List<ExerciseItemProjection> exercise) {
        exercises.addAll(exercise);
    }
}
