package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.common.error.SecurityErrorCode.INVALID_ID;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.annotation.HasRole;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseItemUpdateDto;
import pl.edu.pja.trainmate.core.domain.training.TrainingUnitFacade;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitDto;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitUpdateDto;
import pl.edu.pja.trainmate.core.domain.training.querydsl.TrainingUnitProjection;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/training")
public class TrainingUnitController {

    private final TrainingUnitFacade trainingUnitFacade;

    @Operation(summary = "get training units for current week")
    @ApiResponse(
        responseCode = "200",
        description = "Got training units for current week",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = TRAINED_PERSON)
    @GetMapping("/current")
    public List<TrainingUnitProjection> getCurrentTrainingUnits() {
        log.debug("Request to GET training units for current week for logged user");
        var result = trainingUnitFacade.getCurrentTrainingUnits();
        log.debug("Successfully GOT training units");
        return result;
    }

    @Operation(summary = "get training units for given week by workout plan id and week number")
    @ApiResponse(
        responseCode = "200",
        description = "Got training units for given week by workout plan id and week number",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = {
        PERSONAL_TRAINER
    })
    @GetMapping("/{workoutPlanId}/get-for-week")
    public List<TrainingUnitProjection> getTrainingsByWorkoutPlanIdAndWeek(@PathVariable Long workoutPlanId, @RequestParam Long week) {
        log.debug("Request to GET training units from workout plan with id: {} and week: {}", workoutPlanId, week);
        var result = trainingUnitFacade.getTrainingUnitsByWorkoutPlanIdAndWeek(workoutPlanId, week);
        log.debug("Successfully GOT training units from workout plan with id: {} and week: {}", workoutPlanId, week);
        return result;
    }

    @Operation(summary = "create training unit")
    @ApiResponse(
        responseCode = "200",
        description = "Training unit created",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PostMapping("/create")
    public ResultDto<Long> createTrainingUnit(@RequestBody TrainingUnitDto trainingUnitDto) {
        log.debug("Request to CREATE training unit");
        var result = trainingUnitFacade.create(trainingUnitDto);
        log.debug("CREATED training unit with id: {}", result.getValue());
        return result;
    }

    @Operation(summary = "update training unit")
    @ApiResponse(
        responseCode = "200",
        description = "training unit updated",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PutMapping("/{id}/update")
    public void updateTrainingUnit(@PathVariable Long id, @RequestBody TrainingUnitUpdateDto trainingUnitDto) {
        log.debug("Request to UPDATE training unit with id: {}", id);
        validateId(id, trainingUnitDto.getId());
        trainingUnitFacade.updateTrainingUnit(trainingUnitDto);
        log.debug("UPDATED training unit with id: {}", id);
    }

    @Operation(summary = "delete training unit")
    @ApiResponse(
        responseCode = "200",
        description = "training unit deleted",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @DeleteMapping("/{trainingUnitId}/delete")
    public void deleteTrainingUnit(@PathVariable Long trainingUnitId, @RequestBody BasicAuditDto dto) {
        log.debug("Request to DELETE training unit with id: {}", trainingUnitId);
        validateId(trainingUnitId, dto.getId());
        trainingUnitFacade.deleteTrainingUnit(dto);
        log.debug("DELETED training unit");
    }

    @Operation(summary = "update exercise item")
    @ApiResponse(
        responseCode = "200",
        description = "exercise item updated",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PutMapping("/exercise/{id}/update")
    public void updateExerciseItem(@PathVariable Long id, @RequestBody ExerciseItemUpdateDto exerciseItemUpdateDto) {
        log.debug("Request to UPDATE exercise item with id: {}", id);
        validateId(id, exerciseItemUpdateDto.getId());
        trainingUnitFacade.updateExerciseItem(exerciseItemUpdateDto);
        log.debug("UPDATED exercise item with id: {}", id);

    }

    @Operation(summary = "delete exercise item")
    @ApiResponse(
        responseCode = "200",
        description = "exercise item deleted",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @DeleteMapping("/exercise/{exerciseItemId}/delete")
    public void deleteExerciseItem(@PathVariable Long exerciseItemId, @RequestBody BasicAuditDto dto) {
        log.debug("Request to DELETE exercise item with id: {}", exerciseItemId);
        validateId(exerciseItemId, dto.getId());
        trainingUnitFacade.deleteExerciseItem(dto);
        log.debug("DELETED exercise item");
    }

    private void validateId(Long applicationId, Long idFromDto) {
        if (!applicationId.equals(idFromDto)) {
            throw new CommonException(INVALID_ID);
        }
    }
}
