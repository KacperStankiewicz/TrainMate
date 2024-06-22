package pl.edu.pja.trainmate.core.domain.exercise.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.common.Muscle;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
@Value
public class ExerciseCreateDto implements ExerciseData {

    String name;
    String description;
    String url;
    Muscle muscleInvolved;
}
