package pl.edu.pja.trainmate.core.domain.report;

import static pl.edu.pja.trainmate.core.common.ResultStatus.SUCCESS;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.file.FileFacade;
import pl.edu.pja.trainmate.core.domain.report.dto.PeriodicalReportCreateDto;
import pl.edu.pja.trainmate.core.domain.report.dto.PeriodicalReportUpdateDto;
import pl.edu.pja.trainmate.core.domain.report.querydsl.PeriodicalReportProjection;
import pl.edu.pja.trainmate.core.domain.user.MenteeFacade;

@Service
@RequiredArgsConstructor
public class ReportFacade {

    private final ReportService reportService;
    private final MenteeFacade menteeFacade;
    private final FileFacade fileFacade;

    public PeriodicalReportProjection getReportById(Long reportId) {
        return reportService.getReportById(reportId);
    }

    public PeriodicalReportProjection getInitialReportByUserId(String keycloakId) {
        return reportService.getInitialReportForUser(keycloakId);
    }

    public List<PeriodicalReportProjection> getAllReportsForLoggedUser() {
        return reportService.getAllReportsForLoggedUser();
    }

    public List<PeriodicalReportProjection> getAllReportsByUserKeycloakId(String keycloakId) {
        return reportService.getAllReportsByUserId(keycloakId);
    }

    public ResultDto<Long> createPeriodicalReport(PeriodicalReportCreateDto reportCreateDto) {
        var result = reportService.createPeriodicalReport(reportCreateDto);
        if (SUCCESS.equals(result.getStatus())) {
            fileFacade.addFiles(result.getValue(), reportCreateDto.getImages());
        }

        return result;
    }

    public void updatePeriodicalReport(PeriodicalReportUpdateDto reportDto) {
        reportService.updatePeriodicalReport(reportDto);

        fileFacade.deleteAllFilesByReportId(reportDto.getReportId());
        fileFacade.addFiles(reportDto.getReportId(), reportDto.getImages());
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
