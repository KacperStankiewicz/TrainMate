package pl.edu.pja.trainmate.core.domain.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.report.dto.PeriodicalReportCreateDto;

@Service
@RequiredArgsConstructor
public class ReportFacade {

    private final ReportService reportService;

    public ResultDto<Long> createPeriodicalReport(PeriodicalReportCreateDto reportCreateDto) {
        return reportService.createPeriodicalReport(reportCreateDto);
    }

    public void reviewReport(BasicAuditDto dto) {
        reportService.markReportAsReviewed(dto);
    }

    public ResultDto<Long> createInitialReport(PeriodicalReportCreateDto reportCreateDto) {
        return reportService.createInitialReport(reportCreateDto);
    }
}
