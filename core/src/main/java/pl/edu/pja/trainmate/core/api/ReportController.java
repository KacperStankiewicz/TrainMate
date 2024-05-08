package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.annotation.HasRole;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.report.ReportFacade;
import pl.edu.pja.trainmate.core.domain.report.dto.PeriodicalReportCreateDto;
import pl.edu.pja.trainmate.core.domain.report.dto.ReportCreateDto;
import pl.edu.pja.trainmate.core.domain.training.TrainingUnitFacade;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReportController {

    private final ReportFacade reportFacade;
    private final TrainingUnitFacade trainingUnitFacade;

    @Operation(summary = "create periodical report")
    @ApiResponse(
        responseCode = "200",
        description = "created periodical report",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = TRAINED_PERSON)
    @PostMapping("/workout-plan/{workoutPlanId}/report")
    public ResultDto<Long> createReport(@PathVariable Long workoutPlanId, @RequestBody PeriodicalReportCreateDto reportCreateDto) {
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
    public void reviewPeriodicalReport(@PathVariable Long reportId) {
        log.debug("Request to review periodical report with id: {}", reportId);
        reportFacade.reviewReport(reportId);
        log.debug("Reviewed periodical report with id: {}", reportId);
    }

    @Operation(summary = "create exercise item report")
    @ApiResponse(
        responseCode = "200",
        description = "created exercise item report",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = TRAINED_PERSON)
    @PostMapping("/exercise/{id}/report")
    public void createReport(@PathVariable Long id, @RequestBody ReportCreateDto reportCreateDto) {
        log.debug("Request to CREATE report for exercise item with id: {}", reportCreateDto.getExerciseItemId());
        validateId(id, reportCreateDto.getExerciseItemId());
        trainingUnitFacade.addReport(reportCreateDto);
        log.debug("CREATED report for exercise item with id: {}", reportCreateDto.getExerciseItemId());
    }

    @Operation(summary = "review exercise item report")
    @ApiResponse(
        responseCode = "200",
        description = "marked exercise item report as reviewed",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PostMapping("/exercise/{id}/review-report")
    public void reviewReport(@PathVariable Long id) {
        log.debug("Request to review report for exercise item with id: {}", id);
        trainingUnitFacade.reviewReport(id);
        log.debug("Reviewed report for exercise item with id: {}", id);
    }

    private void validateId(Long bodyId, Long id) {
        if (!Objects.equals(bodyId, id)) {
            throw new IllegalArgumentException("Id in path and body must be the same");
        }
    }
}
