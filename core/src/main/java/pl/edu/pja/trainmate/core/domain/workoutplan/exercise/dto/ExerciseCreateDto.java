package pl.edu.pja.trainmate.core.domain.workoutplan.exercise.dto;

import lombok.Value;

@Value
public class ExerciseCreateDto {
    String name;
    String description;
    String url;
}
