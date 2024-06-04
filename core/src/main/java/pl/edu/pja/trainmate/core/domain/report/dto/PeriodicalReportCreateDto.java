package pl.edu.pja.trainmate.core.domain.report.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
public class PeriodicalReportCreateDto {

    Long workoutPlanId;
    Double weight;
    Integer bodyFat;
    Double leftBiceps;
    Double rightBiceps;
    Double leftForearm;
    Double rightForearm;
    Double leftThigh;
    Double rightThigh;
    Double leftCalf;
    Double rightCalf;

    Double shoulders;
    Double chest;
    Double waist;
    Double abdomen;
    Double hips;
}
