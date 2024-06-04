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
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.WorkoutPlanFacade;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.AllWorkoutData;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanCreateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanDto;

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
    @GetMapping("/{id}")
    public AllWorkoutData getWorkoutPlanData(@PathVariable Long id) {
        return workoutPlanFacade.get(id);
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
        return workoutPlanFacade.create(workoutPlanCreateDto);
    }

    @Operation(summary = "update workout plan")
    @ApiResponse(
        responseCode = "200",
        description = "updated workout plan",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PutMapping("/{workoutPlanId}/update")
    public void updateWorkoutPlan(@PathVariable Long workoutPlanId, @RequestBody WorkoutPlanDto workoutPlanDto) {
        validateId(workoutPlanId, workoutPlanDto.getId());
        workoutPlanFacade.update(workoutPlanDto);
    }

    @Operation(summary = "delete workout plan")
    @ApiResponse(
        responseCode = "200",
        description = "deleted workout plan",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @DeleteMapping("/{id}/delete")
    public void deleteWorkoutPlan(@PathVariable Long id) {
        workoutPlanFacade.delete(id);
    }

    private void validateId(Long bodyId, Long id) {
        if (!Objects.equals(bodyId, id)) {
            throw new IllegalArgumentException("Id in path and body must be the same");
        }
    }
}
