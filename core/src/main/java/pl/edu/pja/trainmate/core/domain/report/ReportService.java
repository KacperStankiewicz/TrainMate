package pl.edu.pja.trainmate.core.domain.report;

import static pl.edu.pja.trainmate.core.common.error.ReportErrorCode.COULD_NOT_CREATE_REPORT;
import static pl.edu.pja.trainmate.core.common.error.ReportErrorCode.INITIAL_REPORT_ALREADY_EXISTS;
import static pl.edu.pja.trainmate.core.common.error.ReportErrorCode.WORKOUT_PLAN_WAS_ALREADY_REPORTED;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataProvider;
import pl.edu.pja.trainmate.core.domain.report.dto.PeriodicalReportCreateDto;
import pl.edu.pja.trainmate.core.domain.report.querydsl.PeriodicalReportProjection;
import pl.edu.pja.trainmate.core.domain.training.querydsl.ReportQueryService;

@Service
@RequiredArgsConstructor
class ReportService {

    private final ReportRepository repository;
    private final LoggedUserDataProvider loggedUserDataProvider;
    private final ReportQueryService queryService;

    public List<PeriodicalReportProjection> getAllReportsForLoggedUser() {
        var userId = loggedUserDataProvider.getLoggedUserId();

        return queryService.getReportsByUserId(userId);
    }

    public ResultDto<Long> createPeriodicalReport(PeriodicalReportCreateDto reportCreateDto) {
        if (repository.existsReportEntityByWorkoutPlanId(reportCreateDto.getWorkoutPlanId())) {
            throw new CommonException(WORKOUT_PLAN_WAS_ALREADY_REPORTED.format(reportCreateDto.getWorkoutPlanId()),
                WORKOUT_PLAN_WAS_ALREADY_REPORTED.getHttpStatus());
        }

        var userId = loggedUserDataProvider.getLoggedUserId();
        var entity = buildReportEntity(reportCreateDto, userId).build();

        return ResultDto.ofValueOrError(repository.save(entity).getId(), COULD_NOT_CREATE_REPORT);
    }

    public ResultDto<Long> createInitialReport(PeriodicalReportCreateDto reportCreateDto) {
        var userId = loggedUserDataProvider.getLoggedUserId();
        if (repository.existsReportEntityByUserIdAndInitialIsTrue(userId)) {
            throw new CommonException(INITIAL_REPORT_ALREADY_EXISTS);
        }

        var entity = buildReportEntity(reportCreateDto, userId)
            .initial(true)
            .reviewed(true)
            .build();

        return ResultDto.ofValueOrError(repository.save(entity).getId(), COULD_NOT_CREATE_REPORT);
    }

    public void markReportAsReviewed(BasicAuditDto dto) {
        var entity = repository.findExactlyOneById(dto.getId());
        entity.validateVersion(dto.getVersion());

        entity.markAsReviewed();
        repository.saveAndFlush(entity);
    }

    private ReportEntity.ReportEntityBuilder buildReportEntity(PeriodicalReportCreateDto reportCreateDto, UserId userId) {
        return ReportEntity.builder()
            .workoutPlanId(reportCreateDto.getWorkoutPlanId())
            .userId(userId)
            .weight(reportCreateDto.getWeight())
            .bodyFat(reportCreateDto.getBodyFat())
            .circumferences(BodyCircumferences.builder()
                .rightBiceps(reportCreateDto.getRightBiceps())
                .leftBiceps(reportCreateDto.getLeftBiceps())
                .rightForearm(reportCreateDto.getRightForearm())
                .leftForearm(reportCreateDto.getLeftForearm())
                .rightThigh(reportCreateDto.getRightThigh())
                .leftThigh(reportCreateDto.getLeftThigh())
                .rightCalf(reportCreateDto.getRightCalf())
                .leftCalf(reportCreateDto.getLeftCalf())
                .shoulders(reportCreateDto.getShoulders())
                .chest(reportCreateDto.getChest())
                .waist(reportCreateDto.getWaist())
                .abdomen(reportCreateDto.getAbdomen())
                .hips(reportCreateDto.getHips())
                .build());
    }
}
