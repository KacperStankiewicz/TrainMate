package pl.edu.pja.trainmate.core.api.sampledata;

import java.util.List;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseReport;
import pl.edu.pja.trainmate.core.domain.exercise.SetParams;
import pl.edu.pja.trainmate.core.domain.report.dto.PeriodicalReportCreateDto;
import pl.edu.pja.trainmate.core.domain.report.dto.ReportCreateDto;

public class ReportSampleData {

    public static PeriodicalReportCreateDto.PeriodicalReportCreateDtoBuilder getSamplePeriodicalReportCreateDtoBuilder() {
        return PeriodicalReportCreateDto.builder()
            .workoutPlanId(1L)
            .weight(90.0)
            .bodyFat(12)
            .leftBiceps(1d)
            .rightBiceps(2d)
            .leftForearm(3d)
            .rightForearm(4d)
            .leftThigh(5d)
            .rightThigh(6d)
            .leftCalf(7d)
            .rightCalf(8d)
            .shoulders(9d)
            .chest(10d)
            .waist(12d)
            .abdomen(13d)
            .hips(13d);
    }

    public static ReportCreateDto.ReportCreateDtoBuilder getExerciseReportSampleDataBuilder() {
        return ReportCreateDto.builder()
            .exerciseItemId(1L)
            .sets(List.of(getSampleSetParamsBuilder().build()))
            .remarks("Brak");
    }

    public static ExerciseReport.ExerciseReportBuilder getExerciseReportBuilder() {
        return ExerciseReport.builder()
            .reportedSets(List.of(getSampleSetParamsBuilder().build()))
            .remarks("Brak");
    }

    private static SetParams.SetParamsBuilder getSampleSetParamsBuilder() {
        return SetParams.builder()
            .reportedRepetitions(1)
            .reportedWeight(2)
            .reportedRir(3)
            .set(1);
    }
}
