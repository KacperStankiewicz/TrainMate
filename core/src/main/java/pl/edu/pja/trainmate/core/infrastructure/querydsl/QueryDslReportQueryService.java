package pl.edu.pja.trainmate.core.infrastructure.querydsl;

import com.querydsl.core.BooleanBuilder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BaseJpaQueryService;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.domain.report.QReportEntity;
import pl.edu.pja.trainmate.core.domain.report.querydsl.PeriodicalReportProjection;
import pl.edu.pja.trainmate.core.domain.report.querydsl.QPeriodicalReportProjection;
import pl.edu.pja.trainmate.core.domain.training.querydsl.ReportQueryService;

@Service
@RequiredArgsConstructor
public class QueryDslReportQueryService extends BaseJpaQueryService implements ReportQueryService {

    private static final QReportEntity report = QReportEntity.reportEntity;

    @Override
    public List<PeriodicalReportProjection> getReportsByUserId(UserId userId) {
        return queryFactory()
            .select(new QPeriodicalReportProjection(
                report.initial,
                report.reviewed,
                report.weight,
                report.bodyFat,
                report.circumferences.leftBiceps,
                report.circumferences.rightBiceps,
                report.circumferences.leftForearm,
                report.circumferences.rightForearm,
                report.circumferences.leftThigh,
                report.circumferences.rightThigh,
                report.circumferences.leftCalf,
                report.circumferences.rightCalf,
                report.circumferences.shoulders,
                report.circumferences.chest,
                report.circumferences.waist,
                report.circumferences.abdomen,
                report.circumferences.hips,
                report.creationDateTime
            ))
            .from(report)
            .where(new BooleanBuilder()
                .and(report.userId.eq(userId))
            )
            .orderBy(report.creationDateTime.asc())
            .fetch();
    }
}
