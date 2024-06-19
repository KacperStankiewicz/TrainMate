package pl.edu.pja.trainmate.core.infrastructure.querydsl;

import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BaseJpaQueryService;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataDto;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataProvider;
import pl.edu.pja.trainmate.core.domain.report.QReportEntity;
import pl.edu.pja.trainmate.core.domain.report.querydsl.PeriodicalReportProjection;
import pl.edu.pja.trainmate.core.domain.report.querydsl.QPeriodicalReportProjection;
import pl.edu.pja.trainmate.core.domain.training.querydsl.ReportQueryService;

@Service
@RequiredArgsConstructor
public class QueryDslReportQueryService extends BaseJpaQueryService implements ReportQueryService {

    private static final QReportEntity report = QReportEntity.reportEntity;

    private final LoggedUserDataProvider userProvider;

    @Override
    public List<PeriodicalReportProjection> getReportsByUserId(UserId userId) {
        return queryFactory()
            .select(buildPeriodicalReportProjection())
            .from(report)
            .where(new BooleanBuilder()
                .and(report.userId.eq(userId))
            )
            .orderBy(report.creationDateTime.asc())
            .fetch();
    }

    @Override
    public PeriodicalReportProjection getReportById(Long reportId) {
        var userDetails = userProvider.getUserDetails();

        return queryFactory()
            .select(buildPeriodicalReportProjection())
            .from(report)
            .where(new BooleanBuilder()
                .and(report.id.eq(reportId))
                .and(prepareUserPredicate(userDetails))
            )
            .fetchOne();
    }

    private QPeriodicalReportProjection buildPeriodicalReportProjection() {
        return new QPeriodicalReportProjection(
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
        );
    }

    private Predicate prepareUserPredicate(LoggedUserDataDto userDetails) {
        var predicate = new BooleanBuilder();

        if (PERSONAL_TRAINER.equals(userDetails.getRole())) {
            return predicate;
        }

        return predicate.and(report.userId.keycloakId.eq(userDetails.getUserId().getKeycloakId()));
    }
}
