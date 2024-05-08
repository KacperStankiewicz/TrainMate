package pl.edu.pja.trainmate.core.domain.report.dto;

import lombok.Value;

@Value
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
