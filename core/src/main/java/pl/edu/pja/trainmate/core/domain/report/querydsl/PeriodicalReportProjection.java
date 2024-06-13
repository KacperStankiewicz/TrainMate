package pl.edu.pja.trainmate.core.domain.report.querydsl;

import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(access = PRIVATE, force = true)
public class PeriodicalReportProjection {

    boolean initial;
    boolean reviewed;
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
    LocalDate createdDate;

    @QueryProjection
    public PeriodicalReportProjection(
        boolean initial,
        boolean reviewed,
        Double weight,
        Integer bodyFat,
        Double leftBiceps,
        Double rightBiceps,
        Double leftForearm,
        Double rightForearm,
        Double leftThigh,
        Double rightThigh,
        Double leftCalf,
        Double rightCalf,
        Double shoulders,
        Double chest,
        Double waist,
        Double abdomen,
        Double hips,
        LocalDateTime createdDate) {
        this.initial = initial;
        this.reviewed = reviewed;
        this.weight = weight;
        this.bodyFat = bodyFat;
        this.leftBiceps = leftBiceps;
        this.rightBiceps = rightBiceps;
        this.leftForearm = leftForearm;
        this.rightForearm = rightForearm;
        this.leftThigh = leftThigh;
        this.rightThigh = rightThigh;
        this.leftCalf = leftCalf;
        this.rightCalf = rightCalf;
        this.shoulders = shoulders;
        this.chest = chest;
        this.waist = waist;
        this.abdomen = abdomen;
        this.hips = hips;
        this.createdDate = createdDate.toLocalDate();
    }
}

