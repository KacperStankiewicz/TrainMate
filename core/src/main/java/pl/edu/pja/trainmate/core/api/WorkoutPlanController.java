package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/workout-plan")
public class WorkoutPlanController {

    private final WorkoutPlanFacade workoutPlanFacade;

    @Operation(summary = "get workout plan")
    @ApiResponse(
        responseCode = "200",
        description = "get workout plan",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = {
        PERSONAL_TRAINER,
        TRAINED_PERSON
    })
    @GetMapping("/{workoutPlanId}")
    public AllWorkoutData getWorkoutPlanData(@PathVariable Long workoutPlanId) {
        log.debug("REST request to GET all workout data by id: {}", workoutPlanId);
        var result = workoutPlanFacade.getById(workoutPlanId);
        log.debug("Successfully got workout plan data");
        return result;
    }

    @Operation(summary = "create workout plan")
    @ApiResponse(
        responseCode = "200",
        description = "created workout plan",
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
        description = "updated workout plan",
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
        description = "deleted workout plan",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @DeleteMapping("/{workoutPlanId}/delete")
    public void deleteWorkoutPlan(@PathVariable Long workoutPlanId, @RequestBody BasicAuditDto dto) {
        log.debug("REST request to DELETE workout plan with id: {}", workoutPlanId);
        validateId(workoutPlanId, dto.getId());
        workoutPlanFacade.delete(workoutPlanId);
        log.debug("Successfully deleted workout plan");
    }

    private void validateId(Long bodyId, Long id) {
        if (!Objects.equals(bodyId, id)) {
            throw new IllegalArgumentException("Id in path and body must be the same");
        }
    }
}
