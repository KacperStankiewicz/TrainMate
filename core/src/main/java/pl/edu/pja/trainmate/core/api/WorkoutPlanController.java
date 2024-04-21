package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.annotation.HasRole;
import pl.edu.pja.trainmate.core.domain.workoutplan.WorkoutPlanFacade;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanCreateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanDto;

@RequiredArgsConstructor
@RestController
@HasRole(roleType = PERSONAL_TRAINER)
@RequestMapping("/workout")
public class WorkoutPlanController {

        private final WorkoutPlanFacade workoutPlanFacade;

        @Operation(summary = "create workout plan")
        @ApiResponse(
            responseCode = "200",
            description = "created workout plan",
            content = @Content(mediaType = "application/json")
        )
        @PostMapping("/create")
        public void createWorkoutPlan(@RequestBody WorkoutPlanCreateDto workoutPlanCreateDto) {
            workoutPlanFacade.create(workoutPlanCreateDto);
        }

        @Operation(summary = "update workout plan")
        @ApiResponse(
            responseCode = "200",
            description = "updated workout plan",
            content = @Content(mediaType = "application/json")
        )
        @PutMapping("/update")
        public void updateWorkoutPlan(@RequestBody WorkoutPlanDto workoutPlanDto) {
            workoutPlanFacade.update(workoutPlanDto);
        }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}
