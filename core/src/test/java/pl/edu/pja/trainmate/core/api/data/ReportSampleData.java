package pl.edu.pja.trainmate.core.api.data;

import pl.edu.pja.trainmate.core.domain.exercise.ExerciseReport;
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
            .repetitions(1)
            .weight(2)
            .rir(3)
            .sets(4)
            .remarks("Brak");
    }

    public static ExerciseReport.ExerciseReportBuilder getExerciseReportBuilder() {
        return ExerciseReport.builder()
            .reportedRepetitions(1)
            .reportedWeight(2)
            .reportedRir(3)
            .reportedSets(4)
            .remarks("Brak");
    }
}
