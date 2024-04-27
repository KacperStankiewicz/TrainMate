package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.annotation.HasRole;
import pl.edu.pja.trainmate.core.domain.training.TrainingUnitFacade;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/training")
public class TrainingUnitController {

    private final TrainingUnitFacade trainingUnitFacade;

    @Operation(summary = "create training unit")
    @ApiResponse(
        responseCode = "200",
        description = "created training unit",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PostMapping("/create")
    public void createTrainingUnit(@RequestBody TrainingUnitDto trainingUnitDto) {
        trainingUnitFacade.create(trainingUnitDto);
    }

    @Operation(summary = "update training unit")
    @ApiResponse(
        responseCode = "200",
        description = "updated training unit",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PutMapping("/update/{id}")
    public void updateTrainingUnit(@RequestBody TrainingUnitDto trainingUnitDto, @RequestParam Long id) {
        if (!Objects.equals(trainingUnitDto.getId(), id)) {
            throw new IllegalArgumentException("Id in path and body must be the same");
        }
        trainingUnitFacade.updateTrainingUnit(trainingUnitDto);
    }

    @Operation(summary = "update exercise item")
    @ApiResponse(
        responseCode = "200",
        description = "updated exercise item",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PutMapping("/exercise/update/{id}")
    public void updateExerciseItem(@RequestBody TrainingUnitDto trainingUnitDto, @RequestParam Long id) {
        if (!Objects.equals(trainingUnitDto.getExerciseId(), id)) {
            throw new IllegalArgumentException("Id in path and body must be the same");
        }
        trainingUnitFacade.updateExerciseItem(trainingUnitDto);
    }

    @Operation(summary = "delete training unit")
    @ApiResponse(
        responseCode = "200",
        description = "deleted training unit",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @DeleteMapping("/delete/{id}")
    public void deleteTrainingUnit(@RequestBody Long bodyId, @RequestParam Long id) {
        if (!Objects.equals(bodyId, id)) {
            throw new IllegalArgumentException("Id in path and body must be the same");
        }
        trainingUnitFacade.deleteTrainingUnit(id);
    }

    @Operation(summary = "delete exercise item")
    @ApiResponse(
        responseCode = "200",
        description = "deleted exercise item",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @DeleteMapping("/exercise/delete/{id}")
    public void deleteExerciseItem(@RequestBody Long bodyId, @RequestParam Long id) {
        if (!Objects.equals(bodyId, id)) {
            throw new IllegalArgumentException("Id in path and body must be the same");
        }
        trainingUnitFacade.deleteExerciseItem(id);
    }
}
