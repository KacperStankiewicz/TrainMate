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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.annotation.HasRole;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.WorkoutPlanFacade;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.AllWorkoutData;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanCreateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanUpdateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanListItemProjection;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanProjection;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/workout-plan")
public class WorkoutPlanController {

    private final WorkoutPlanFacade workoutPlanFacade;

    @Operation(summary = "get workout plan")
    @ApiResponse(
        responseCode = "200",
        description = "Got workout plan",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = {
        PERSONAL_TRAINER,
        MENTEE
    })
    @GetMapping("/{workoutPlanId}")
    public AllWorkoutData getWorkoutPlanData(@PathVariable Long workoutPlanId) {
        log.debug("REST request to GET all workout data by id: {}", workoutPlanId);
        var result = workoutPlanFacade.getById(workoutPlanId);
        log.debug("Successfully got workout plan data");
        return result;
    }

    @Operation(summary = "get workout plan header")
    @ApiResponse(
        responseCode = "200",
        description = "Got workout plan header",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = {
        PERSONAL_TRAINER,
        MENTEE
    })
    @GetMapping("/{workoutPlanId}/header")
    public WorkoutPlanProjection getWorkoutPlanHeader(@PathVariable Long workoutPlanId) {
        log.debug("REST request to GET workout plan header by id: {}", workoutPlanId);
        var result = workoutPlanFacade.getWorkoutPlanHeader(workoutPlanId);
        log.debug("Successfully GOT workout plan header");
        return result;
    }

    @Operation(summary = "get all workout plans for user")
    @ApiResponse(
        responseCode = "200",
        description = "Got all workout plans for user",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @GetMapping("/{keycloakId}/all")
    public List<WorkoutPlanListItemProjection> getAllWorkoutPlansByUserId(@PathVariable String keycloakId) {
        log.debug("REST request to GET all workout plans for user with keycloak id: {}", keycloakId);
        var result = workoutPlanFacade.getAllPlansByUserId(keycloakId);
        log.debug("Successfully GOT all workout plans");
        return result;
    }

    @Operation(summary = "get all workout plans for currently logged user")
    @ApiResponse(
        responseCode = "200",
        description = "Got all workout plans for currently logged user",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = MENTEE)
    @GetMapping("/my-plans")
    public List<WorkoutPlanListItemProjection> getAllWorkoutPlansForCurrentlyLoggedUser() {
        log.debug("REST request to GET all workout plans for currently logged user");
        var result = workoutPlanFacade.getAllPlansForLoggedUser();
        log.debug("Successfully GOT all workout plans for logged user");
        return result;
    }

    @Operation(summary = "create workout plan")
    @ApiResponse(
        responseCode = "200",
        description = "workout plan created",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PostMapping("/create")
    public ResultDto<Long> createWorkoutPlan(@RequestBody WorkoutPlanCreateDto workoutPlanCreateDto) {
        log.debug("REST request to CREATE workout plan");
        var result = workoutPlanFacade.create(workoutPlanCreateDto);
        log.debug("Successfully created workout plan");
        return result;
    }

    @Operation(summary = "update workout plan")
    @ApiResponse(
        responseCode = "200",
        description = "workout plan updated",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PutMapping("/{workoutPlanId}/update")
    public void updateWorkoutPlan(@PathVariable Long workoutPlanId, @RequestBody WorkoutPlanUpdateDto workoutPlanUpdateDto) {
        log.debug("REST request to UPDATE workout plan with id: {}", workoutPlanId);
        validateId(workoutPlanId, workoutPlanUpdateDto.getId());
        workoutPlanFacade.update(workoutPlanUpdateDto);
        log.debug("Successfully updated workout plan");
    }

    @Operation(summary = "delete workout plan")
    @ApiResponse(
        responseCode = "200",
        description = "workout plan deleted",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @DeleteMapping("/{workoutPlanId}/delete")
    public void deleteWorkoutPlan(@PathVariable Long workoutPlanId, @RequestBody BasicAuditDto dto) {
        log.debug("REST request to DELETE workout plan with id: {}", workoutPlanId);
        validateId(workoutPlanId, dto.getId());
        workoutPlanFacade.delete(dto);
        log.debug("Successfully deleted workout plan");
    }

    private void validateId(Long bodyId, Long id) {
        if (!Objects.equals(bodyId, id)) {
            throw new IllegalArgumentException("Id in path and body must be the same");
        }
    }
}
