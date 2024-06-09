package pl.edu.pja.trainmate.core.domain.training.querydsl;

import java.util.List;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.domain.report.querydsl.PeriodicalReportProjection;

public interface ReportQueryService {

    List<PeriodicalReportProjection> getReportsByUserId(UserId userId);
}
