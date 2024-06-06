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
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.annotation.HasRole;
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
        description = "get training units for current week",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = {
        TRAINED_PERSON
    })
    @GetMapping("/current")
    public List<TrainingUnitProjection> getCurrentWorkoutPlanData() {
        return trainingUnitFacade.getCurrentTraining();
    }

    @Operation(summary = "create training unit")
    @ApiResponse(
        responseCode = "200",
        description = "created training unit",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PostMapping("/create")
    public ResultDto<Long> createTrainingUnit(@RequestBody TrainingUnitDto trainingUnitDto) {
        return trainingUnitFacade.create(trainingUnitDto);
    }

    @Operation(summary = "update training unit")
    @ApiResponse(
        responseCode = "200",
        description = "updated training unit",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PutMapping("/{id}/update")
    public void updateTrainingUnit(@PathVariable Long id, @RequestBody TrainingUnitUpdateDto trainingUnitDto) {
        validateId(id, trainingUnitDto.getId());
        trainingUnitFacade.updateTrainingUnit(trainingUnitDto);
    }

    @Operation(summary = "delete training unit")
    @ApiResponse(
        responseCode = "200",
        description = "deleted training unit",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @DeleteMapping("/{id}/delete")
    public void deleteTrainingUnit(@PathVariable Long id) {
        trainingUnitFacade.deleteTrainingUnit(id);
    }

    @Operation(summary = "update exercise item")
    @ApiResponse(
        responseCode = "200",
        description = "updated exercise item",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PutMapping("/exercise/{id}/update")
    public void updateExerciseItem(@PathVariable Long id, @RequestBody ExerciseItemUpdateDto exerciseItemUpdateDto) {
        validateId(id, exerciseItemUpdateDto.getId());
        trainingUnitFacade.updateExerciseItem(exerciseItemUpdateDto);
    }

    @Operation(summary = "delete exercise item")
    @ApiResponse(
        responseCode = "200",
        description = "deleted exercise item",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @DeleteMapping("/exercise/{id}/delete")
    public void deleteExerciseItem(@PathVariable Long id) {
        trainingUnitFacade.deleteExerciseItem(id);
    }

    private void validateId(Long applicationId, Long idFromDto) {
        if (!applicationId.equals(idFromDto)) {
            throw new CommonException(INVALID_ID);
        }
    }
}
