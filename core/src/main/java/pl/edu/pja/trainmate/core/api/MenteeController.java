package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.annotation.HasRole;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.report.ReportFacade;
import pl.edu.pja.trainmate.core.domain.report.dto.PeriodicalReportCreateDto;
import pl.edu.pja.trainmate.core.domain.user.MenteeFacade;
import pl.edu.pja.trainmate.core.domain.user.dto.MenteeUpdateDto;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeProjection;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeSearchCriteria;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/mentees")
public class MenteeController {

    private final MenteeFacade menteeFacade;
    private final ReportFacade reportFacade;

    @HasRole(roleType = PERSONAL_TRAINER)
    @PostMapping("/search")
    public Page<MenteeProjection> searchMenteesByCriteria(@RequestBody MenteeSearchCriteria criteria, @Parameter(hidden = true) Pageable pageable) {
        log.debug("Request to search mentees");
        return menteeFacade.search(criteria, pageable);
    }

    @HasRole(roleType = PERSONAL_TRAINER)
    @PostMapping("/invite")
    public ResultDto<Long> inviteMentee(@RequestParam String email) {
        log.debug("Request to invite user with email: {}", email);
        var userId = menteeFacade.invite(email);
        log.debug("User with email: {} was invited", email);
        return userId;
    }

    @HasRole(roleType = {
        PERSONAL_TRAINER,
        TRAINED_PERSON
    })
    @PutMapping("/update-personal-data")
    public void updateMenteePersonalData(@RequestBody MenteeUpdateDto menteeUpdateDto) {
        log.debug("Request to UPDATE mentee personal data");
        menteeFacade.updatePersonalData(menteeUpdateDto);
    }

    @Operation(summary = "create initial report")
    @ApiResponse(
        responseCode = "200",
        description = "created initial report",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = TRAINED_PERSON)
    @PostMapping("/initial-report")
    public ResultDto<Long> createInitialReport(@RequestBody PeriodicalReportCreateDto reportCreateDto) {
        log.debug("Request to CREATE initial report");
        var result = reportFacade.createInitialReport(reportCreateDto);
        log.debug("CREATED initial report with id: {}", result.getValue());
        return result;
    }

    @HasRole(roleType = PERSONAL_TRAINER)
    @PostMapping("/{userId}/deactivate")
    public void deactivateAccount(@PathVariable String userId) {
        log.debug("Request to deactivate mentee account with id: {}", userId);
        menteeFacade.deleteAccount(userId);
        log.debug("Successfully deactivated mentee account with id: {}", userId);
    }

    @HasRole(roleType = PERSONAL_TRAINER)
    @PostMapping("/{userId}/activate")
    public void deleteAccount(@PathVariable String userId) {
        log.debug("Request to activate mentee account with id: {}", userId);
        menteeFacade.activateAccount(userId);
        log.debug("Successfully activate mentee account with id: {}", userId);
    }
}