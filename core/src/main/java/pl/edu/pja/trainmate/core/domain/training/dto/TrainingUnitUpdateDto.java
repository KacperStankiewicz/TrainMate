package pl.edu.pja.trainmate.core.domain.training.dto;

import static lombok.AccessLevel.PRIVATE;

import java.time.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseItemCreateDto;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
public class TrainingUnitUpdateDto {

    Long id;
    Long version;
    DayOfWeek dayOfWeek;
    Long weekNumber;
    ExerciseItemCreateDto exerciseCreateDto;
}
