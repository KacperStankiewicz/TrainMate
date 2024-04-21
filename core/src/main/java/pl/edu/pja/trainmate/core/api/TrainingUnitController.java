package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.annotation.HasRole;
import pl.edu.pja.trainmate.core.domain.training.TrainingUnitFacade;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitCreateDto;
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
    public void createTrainingUnit(@RequestBody TrainingUnitCreateDto trainingUnitCreateDto) {
        trainingUnitFacade.create(trainingUnitCreateDto);
    }

    @Operation(summary = "update training unit")
    @ApiResponse(
        responseCode = "200",
        description = "updated training unit",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PutMapping("/update")
    public void updateTrainingUnit(@RequestBody TrainingUnitDto trainingUnitDto) {
        trainingUnitFacade.update(trainingUnitDto);
    }
}
