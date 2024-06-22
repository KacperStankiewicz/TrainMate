package pl.edu.pja.trainmate.core.domain.exercise.querydsl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.common.Muscle;
import pl.edu.pja.trainmate.core.common.Muscle.MuscleGroup;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ExerciseSearchCriteria {

    String name;
    Muscle muscle;
    MuscleGroup muscleGroup;
}
