package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.common.error.SecurityErrorCode.INVALID_ID;
import static pl.edu.pja.trainmate.core.config.security.RoleType.MENTEE;
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
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseFacade;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseCreateDto;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseDto;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseListItemProjection;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseProjection;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.ExerciseSearchCriteria;

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

    @Operation(summary = "Get exercise by id")
    @ApiResponse(
        responseCode = "200",
        description = "Got exercise by Id",
        content = @Content(mediaType = "application/json")
    )
    @HasRole(roleType = {
        PERSONAL_TRAINER,
        MENTEE
    })
    @GetMapping("/{exerciseId}")
    public ExerciseProjection getExerciseById(@PathVariable Long exerciseId) {
        log.debug("REST request to get exercise by id");
        var result = facade.getById(exerciseId);
        log.debug("Successfully got exercise");
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
        validateId(exerciseId, dto.getId());
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
    public void deleteExercise(@PathVariable Long exerciseId, @RequestBody BasicAuditDto dto) {
        log.debug("REST request to DELETE exercise with id: {}", exerciseId);
        validateId(exerciseId, dto.getId());
        facade.delete(dto);
        log.debug("Successfully DELETED exercise");
    }

    private void validateId(Long id, Long idFromDto) {
        if (!id.equals(idFromDto)) {
            throw new CommonException(INVALID_ID);
        }
    }

}
