package pl.edu.pja.trainmate.core.domain.training.querydsl;

import java.util.List;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.domain.report.querydsl.PeriodicalReportProjection;

public interface ReportQueryService {

    PeriodicalReportProjection getReportById(Long reportId);

    PeriodicalReportProjection getInitialReportByUserId(UserId userId);

    List<PeriodicalReportProjection> getReportsByUserId(UserId userId);
}
