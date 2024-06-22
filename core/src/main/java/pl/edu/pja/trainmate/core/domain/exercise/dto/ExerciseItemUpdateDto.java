package pl.edu.pja.trainmate.core.domain.exercise.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
public class ExerciseItemUpdateDto {

    Long id;
    Long version;
    Long trainingUnitId;
    Long exerciseId;
    Integer repetitions;
    String tempo;
    Double weight;
    Integer rir;
    Integer sets;
}
