package pl.edu.pja.trainmate.core.domain.report;

import static pl.edu.pja.trainmate.core.common.ResultStatus.SUCCESS;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.file.FileFacade;
import pl.edu.pja.trainmate.core.domain.report.dto.PeriodicalReportCreateDto;
import pl.edu.pja.trainmate.core.domain.report.querydsl.PeriodicalReportProjection;
import pl.edu.pja.trainmate.core.domain.user.MenteeFacade;

@Service
@RequiredArgsConstructor
public class ReportFacade {

    private final ReportService reportService;
    private final MenteeFacade menteeFacade;
    private final FileFacade fileFacade;

    public List<PeriodicalReportProjection> getAllReportsForLoggedUser() {
        return reportService.getAllReportsForLoggedUser();
    }

    public ResultDto<Long> createPeriodicalReport(PeriodicalReportCreateDto reportCreateDto) {
        return reportService.createPeriodicalReport(reportCreateDto);
    }

    public void reviewReport(BasicAuditDto dto) {
        reportService.markReportAsReviewed(dto);
    }

    public ResultDto<Long> createInitialReport(PeriodicalReportCreateDto reportCreateDto) {
        var result = reportService.createInitialReport(reportCreateDto);

        if (SUCCESS.equals(result.getStatus())) {
            menteeFacade.unsetFirstLoginFlag();
            fileFacade.addFiles(result.getValue(), reportCreateDto.getImages());
        }

        return result;
    }
}
