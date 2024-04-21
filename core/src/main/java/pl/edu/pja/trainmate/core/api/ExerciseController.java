package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.common.error.ExerciseErrorCode.INVALID_EXERCISE_ID;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.annotation.HasRole;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.domain.workoutplan.exercise.ExerciseFacade;
import pl.edu.pja.trainmate.core.domain.workoutplan.exercise.dto.ExerciseCreateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.exercise.dto.ExerciseDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.exercise.dto.ExerciseListItemProjection;
import pl.edu.pja.trainmate.core.domain.workoutplan.exercise.querydsl.ExerciseSearchCriteria;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/exercise")
public class ExerciseController {

    private final ExerciseFacade facade;

    @Operation(summary = "search exercises")
    @ApiResponse(
        responseCode = "200",
        description = "Found exercises",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PostMapping("/search")
    public Page<ExerciseListItemProjection> searchExercisesByCriteria(@RequestBody ExerciseSearchCriteria criteria,
        @Parameter(hidden = true) Pageable pageable) {
        log.debug("REST request to search exercises by criteria");
        var result = facade.searchByCriteria(criteria, pageable);
        log.debug("Successfully found exercises");
        return result;
    }

    @Operation(summary = "create exercise")
    @ApiResponse(
        responseCode = "200",
        description = "created exercise",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PostMapping("/create")
    public ResultDto<Long> createExercise(@RequestBody ExerciseCreateDto dto) {
        log.debug("REST request to CREATE new exercise");
        var result = facade.create(dto);
        log.debug("Successfully created exercise");
        return result;
    }

    @Operation(summary = "update exercise")
    @ApiResponse(
        responseCode = "200",
        description = "Updated exercise",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @PutMapping("/{exerciseId}")
    public void updateExercise(@PathVariable Long exerciseId, @RequestBody ExerciseDto dto) {
        log.debug("REST request to UPDATE exercise with id: {}", exerciseId);
        validateExerciseId(exerciseId, dto.getId());
        facade.update(dto);
        log.debug("Successfully UPDATED exercise");
    }

    @Operation(summary = "delete exercise")
    @ApiResponse(
        responseCode = "200",
        description = "Deleted exercise",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = PERSONAL_TRAINER)
    @DeleteMapping("/{exerciseId}")
    public void deleteExercise(@PathVariable Long exerciseId) {
        log.debug("REST request to DELETE exercise with id: {}", exerciseId);
        facade.delete(exerciseId);
        log.debug("Successfully DELETED exercise");
    }

    private void validateExerciseId(Long applicationId, Long idFromDto) {
        if (!applicationId.equals(idFromDto)) {
            throw new CommonException(INVALID_EXERCISE_ID);
        }
    }

}
