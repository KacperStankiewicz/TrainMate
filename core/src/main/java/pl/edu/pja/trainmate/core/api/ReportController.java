package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.config.security.RoleType.MENTEE;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.annotation.HasRole;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.report.ReportFacade;
import pl.edu.pja.trainmate.core.domain.report.dto.PeriodicalReportCreateDto;
import pl.edu.pja.trainmate.core.domain.report.dto.ReportCreateDto;
import pl.edu.pja.trainmate.core.domain.report.querydsl.PeriodicalReportProjection;
import pl.edu.pja.trainmate.core.domain.training.TrainingUnitFacade;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReportController {

    private final ReportFacade reportFacade;
    private final TrainingUnitFacade trainingUnitFacade;

    @Operation(summary = "get all periodical reports for logged user")
    @ApiResponse(
        responseCode = "200",
        description = "got all periodical reports for logged user",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = MENTEE)
    @GetMapping("/periodical/all-reports")
    public List<PeriodicalReportProjection> getAllPeriodicalReportsForLoggedUser() {
        log.debug("Request to GET all periodical reports for logged user");
        var result = reportFacade.getAllReportsForLoggedUser();
        log.debug("Successfully GOT all periodical reports for logged user");
        return result;
    }

    @Operation(summary = "get all periodical reports by user id")
    @ApiResponse(
        responseCode = "200",
        description = "got all periodical reports by user id",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @GetMapping("/periodical/{keycloakId}/all-reports")
    public List<PeriodicalReportProjection> getAllPeriodicalReportsByUserId(@PathVariable String keycloakId) {
        log.debug("Request to GET all periodical reports for user with id: {}", keycloakId);
        var result = reportFacade.getAllReportsByUserKeycloakId(keycloakId);
        log.debug("Successfully GOT all periodical reports for user");
        return result;
    }

    @Operation(summary = "create periodical report")
    @ApiResponse(
        responseCode = "200",
        description = "created periodical report",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = MENTEE)
    @PostMapping("/workout-plan/{workoutPlanId}/report")
    public ResultDto<Long> createPeriodicalReport(@PathVariable Long workoutPlanId, @RequestBody PeriodicalReportCreateDto reportCreateDto) {
        validateId(workoutPlanId, reportCreateDto.getWorkoutPlanId());
        log.debug("Request to CREATE periodical report for workout plan with id: {}", reportCreateDto.getWorkoutPlanId());
        var result = reportFacade.createPeriodicalReport(reportCreateDto);
        log.debug("CREATED periodical report with id: {}", result.getValue());
        return result;
    }

    @Operation(summary = "review periodical report")
    @ApiResponse(
        responseCode = "200",
        description = "review periodical report",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PostMapping("/workout-plan/report/{reportId}/review")
    public void reviewPeriodicalReport(@PathVariable Long reportId, @RequestBody BasicAuditDto dto) {
        log.debug("Request to review periodical report with id: {}", reportId);
        validateId(reportId, dto.getId());
        reportFacade.reviewReport(dto);
        log.debug("Reviewed periodical report with id: {}", reportId);
    }

    @Operation(summary = "create exercise item report")
    @ApiResponse(
        responseCode = "200",
        description = "created exercise item report",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = MENTEE)
    @PostMapping("/exercise/{exerciseId}/report")
    public void createReport(@PathVariable Long exerciseId, @RequestBody ReportCreateDto reportCreateDto) {
        log.debug("Request to CREATE report for exercise item with id: {}", reportCreateDto.getExerciseItemId());
        validateId(exerciseId, reportCreateDto.getExerciseItemId());
        trainingUnitFacade.addReport(reportCreateDto);
        log.debug("CREATED report for exercise item with id: {}", reportCreateDto.getExerciseItemId());
    }

    private void validateId(Long bodyId, Long id) {
        if (!Objects.equals(bodyId, id)) {
            throw new IllegalArgumentException("Id in path and body must be the same");
        }
    }
}
