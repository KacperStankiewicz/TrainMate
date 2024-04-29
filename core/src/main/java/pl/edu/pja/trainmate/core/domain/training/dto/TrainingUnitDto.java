package pl.edu.pja.trainmate.core.domain.training.dto;

import java.time.DayOfWeek;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TrainingUnitDto {

    Long id;
    Long workoutPlanId;
    DayOfWeek dayOfWeek;
    Long weekNumber;
    Long exerciseId;
    Integer repetitions;
    String tempo;
    Integer weight;
    Integer targetRir;
    Integer actualRir;
    Integer sets;
}
