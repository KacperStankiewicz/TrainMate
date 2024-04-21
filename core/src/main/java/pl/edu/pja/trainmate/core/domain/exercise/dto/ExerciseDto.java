package pl.edu.pja.trainmate.core.domain.exercise.dto;

import lombok.Builder;
import lombok.Value;
import pl.edu.pja.trainmate.core.common.Muscle;

@Value
@Builder
public class ExerciseDto implements ExerciseData {

    Long id;
    String name;
    String description;
    String url;
    Muscle muscleInvolved;
}
