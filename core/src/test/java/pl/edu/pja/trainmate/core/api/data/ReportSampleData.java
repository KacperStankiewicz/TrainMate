package pl.edu.pja.trainmate.core.api.data;

import pl.edu.pja.trainmate.core.domain.report.dto.PeriodicalReportCreateDto;
import pl.edu.pja.trainmate.core.domain.report.dto.ReportCreateDto;

public class ReportSampleData {

    public static PeriodicalReportCreateDto getSamplePeriodicalReportCreateDto() {
        return new PeriodicalReportCreateDto(
            1L,
            90.0,
            12,
            1d,
            1d,
            1d,
            1d,
            1d,
            1d,
            1d,
            1d,
            1d,
            1d,
            1d,
            1d,
            1d
        );
    }

    public static ReportCreateDto getExerciseReportSampleData() {
        return new ReportCreateDto(
            1L,
            1,
            1,
            1,
            1,
            "Brak"
        );
    }
}
