package pl.edu.pja.trainmate.core.domain.workoutplan.dto;

import java.time.LocalDate;

public interface WorkoutPlanDto {


    LocalDate getStartDate();

    String getName();

    String getCategory();

    Long getDurationInWeeks();
}
