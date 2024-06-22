package pl.edu.pja.trainmate.core.api.workoutplan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static pl.edu.pja.trainmate.core.api.sampledata.WorkoutPlanSampleData.getSampleActiveWorkoutPlanEntityBuilder;
import static pl.edu.pja.trainmate.core.api.sampledata.WorkoutPlanSampleData.getSampleInActiveWorkoutPlanEntityBuilder;
import static pl.edu.pja.trainmate.core.api.sampledata.WorkoutPlanSampleData.getSampleWorkoutPlanCreateDtoBuilder;
import static pl.edu.pja.trainmate.core.api.sampledata.WorkoutPlanSampleData.getSampleWorkoutPlanDtoBuilder;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.CREATE;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.DELETE;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.UPDATE;
import static pl.edu.pja.trainmate.core.common.ResultStatus.SUCCESS;
import static pl.edu.pja.trainmate.core.common.error.WorkoutPlanErrorCode.MUST_NOT_CHANGE_ACTIVE_WORKOUT_PLAN;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.testutils.ResponseConverter.castResponseTo;

import java.time.LocalDate;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.WorkoutPlanRepository;

@SpringBootTest
@AutoConfigureMockMvc
class WorkoutPlanControllerIT extends ControllerSpecification {

    @Autowired
    private WorkoutPlanRepository repository;

    @AfterEach
    @SuppressWarnings("unused")
    private void cleanup() {
        repository.deleteAll();
    }

    @Test
    void shouldCreateWorkoutPlan() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var dto = getSampleWorkoutPlanCreateDtoBuilder().build();

        //when
        var response = performPost(CREATE, dto).getResponse();

        //then
        var responseBody = castResponseTo(response, ResultDto.class);
        assertEquals(SUCCESS, responseBody.getStatus());

        //and
        var entity = repository.findExactlyOneById((Long) responseBody.getValue());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getCategory(), entity.getCategory());
        assertEquals(dto.getUserId(), entity.getUserId().toString());
        assertEquals(dto.getStartDate(), entity.getDateRange().getFrom());
        assertEquals(dto.getStartDate().plusWeeks(dto.getDurationInWeeks()), entity.getDateRange().getTo());
    }

    @Test
    @SneakyThrows
    void shouldThrowMustNotUpdateActiveWorkoutPlan() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var entity = repository.save(getSampleActiveWorkoutPlanEntityBuilder().build());
        var dto = getSampleWorkoutPlanDtoBuilder()
            .id(entity.getId())
            .name("name2")
            .category("category2")
            .version(getActualVersion(entity.getId()))
            .build();

        //when
        var response = performPut(String.format(UPDATE, dto.getId()), dto).getResponse();

        //then
        assertEquals(BAD_REQUEST.value(), response.getStatus());

        //and
        assertTrue(response.getContentAsString().contains(MUST_NOT_CHANGE_ACTIVE_WORKOUT_PLAN.toString()));
    }

    @Test
    void shouldUpdateWorkoutPlan() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var entity = repository.save(getSampleInActiveWorkoutPlanEntityBuilder().build());
        var dto = getSampleWorkoutPlanDtoBuilder()
            .id(entity.getId())
            .name("name2")
            .category("category2")
            .durationInWeeks(8L)
            .startDate(LocalDate.now().plusDays(1))
            .version(getActualVersion(entity.getId()))
            .build();

        //when
        var response = performPut(String.format(UPDATE, dto.getId()), dto).getResponse();

        //then
        assertEquals(200, response.getStatus());

        //and
        var savedEntity = repository.findExactlyOneById(dto.getId());
        assertEquals(dto.getName(), savedEntity.getName());
        assertEquals(dto.getCategory(), savedEntity.getCategory());
        assertEquals(entity.getUserId().toString(), savedEntity.getUserId().toString());
        assertEquals(dto.getStartDate(), savedEntity.getDateRange().getFrom());
        assertEquals(dto.getStartDate().plusWeeks(dto.getDurationInWeeks()), savedEntity.getDateRange().getTo());
    }

    @Test
    @SneakyThrows
    void shouldThrowOptimisticLockExceptionWhenUpdatingWorkoutPlan() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var entity = repository.save(getSampleInActiveWorkoutPlanEntityBuilder().build());
        var dto = getSampleWorkoutPlanDtoBuilder()
            .id(entity.getId())
            .name("name2")
            .category("category2")
            .durationInWeeks(8L)
            .startDate(LocalDate.now().plusDays(10))
            .version(9999L)
            .build();

        //when
        var response = performPut(String.format(UPDATE, dto.getId()), dto).getResponse();

        //then
        assertEquals(409, response.getStatus());
        assertTrue(response.getContentAsString().contains("RESOURCE_HAS_BEEN_MODIFIED_BY_ANOTHER_USER"));

        //and
        var savedEntity = repository.findExactlyOneById(dto.getId());
        assertNotEquals(dto.getName(), savedEntity.getName());
        assertNotEquals(dto.getCategory(), savedEntity.getCategory());
        assertNotEquals(dto.getStartDate(), savedEntity.getDateRange().getFrom());
        assertNotEquals(dto.getStartDate().plusWeeks(dto.getDurationInWeeks()), savedEntity.getDateRange().getTo());
    }

    @Test
    void shouldDeleteWorkoutPlan() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var entity = repository.save(getSampleInActiveWorkoutPlanEntityBuilder().build());
        var dto = BasicAuditDto.ofValue(entity.getId(), entity.getVersion());

        //when
        var response = performDelete(String.format(DELETE, entity.getId()), dto).getResponse();

        //then
        assertEquals(200, response.getStatus());

        //and
        assertEquals(0, repository.findAll().size());
    }

    @Test
    @SneakyThrows
    void shouldThrowOptimisticLockExceptionWhenDeletingWorkoutPlan() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var entity = repository.save(getSampleInActiveWorkoutPlanEntityBuilder().build());
        var dto = BasicAuditDto.ofValue(entity.getId(), 99999L);

        //when
        var response = performDelete(String.format(DELETE, entity.getId()), dto).getResponse();

        //then
        assertEquals(409, response.getStatus());
        assertTrue(response.getContentAsString().contains("RESOURCE_HAS_BEEN_MODIFIED_BY_ANOTHER_USER"));

        //and
        assertNotEquals(0, repository.findAll().size());
    }

    @Test
    @SneakyThrows
    void shouldThrowMustNotUpdateActiveWorkoutPlanWhenDeleting() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var entity = repository.save(getSampleActiveWorkoutPlanEntityBuilder().build());
        var dto = BasicAuditDto.ofValue(entity.getId(), entity.getVersion());

        //when
        var response = performDelete(String.format(DELETE, entity.getId()), dto).getResponse();

        //then
        assertEquals(BAD_REQUEST.value(), response.getStatus());

        //and
        assertTrue(response.getContentAsString().contains(MUST_NOT_CHANGE_ACTIVE_WORKOUT_PLAN.toString()));
    }

    Long getActualVersion(Long id) {
        return repository.findExactlyOneById(id).getVersion();
    }
}
