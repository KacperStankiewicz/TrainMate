package pl.edu.pja.trainmate.core.api.workoutplan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static pl.edu.pja.trainmate.core.api.data.WorkoutPlanSampleData.getSampleActiveWorkoutPlanEntityBuilder;
import static pl.edu.pja.trainmate.core.api.data.WorkoutPlanSampleData.getSampleInActiveWorkoutPlanEntityBuilder;
import static pl.edu.pja.trainmate.core.api.data.WorkoutPlanSampleData.getSampleWorkoutPlanCreateDtoBuilder;
import static pl.edu.pja.trainmate.core.api.data.WorkoutPlanSampleData.getSampleWorkoutPlanDtoBuilder;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.CREATE;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.DELETE;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.UPDATE;
import static pl.edu.pja.trainmate.core.common.ResultStatus.SUCCESS;
import static pl.edu.pja.trainmate.core.common.error.WorkoutPlanErrorCode.MUST_NOT_CHANGE_ACTIVE_WORKOUT_PLAN;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.utils.ResponseConverter.castResponseTo;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.WorkoutPlanRepository;

@SpringBootTest
@AutoConfigureMockMvc
class WorkoutPlanControllerIT extends ControllerSpecification {

    @Autowired
    private WorkoutPlanRepository repository;

    @AfterEach
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
    void shouldThrowMustNotUpdateActiveWorkoutPlan() throws UnsupportedEncodingException {
        //given
        userWithRole(PERSONAL_TRAINER);
        var entity = repository.save(getSampleActiveWorkoutPlanEntityBuilder().build());
        var dto = getSampleWorkoutPlanDtoBuilder()
            .id(entity.getId())
            .name("name2")
            .category("category2")
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
            .build();

        //when
        var response = performPut(String.format(UPDATE, dto.getId()), dto).getResponse();

        //then
        assertEquals(OK.value(), response.getStatus());

        //and
        var savedEntity = repository.findExactlyOneById(dto.getId());
        assertEquals(dto.getName(), savedEntity.getName());
        assertEquals(dto.getCategory(), savedEntity.getCategory());
        assertEquals(entity.getUserId().toString(), savedEntity.getUserId().toString());
        assertEquals(dto.getStartDate(), savedEntity.getDateRange().getFrom());
        assertEquals(dto.getStartDate().plusWeeks(dto.getDurationInWeeks()), savedEntity.getDateRange().getTo());
    }

    @Test
    void shouldDeleteWorkoutPlan() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var entity = repository.save(getSampleInActiveWorkoutPlanEntityBuilder().build());

        //when
        var response = performDelete(String.format(DELETE, entity.getId())).getResponse();

        //then
        assertEquals(OK.value(), response.getStatus());

        //and
        assertEquals(0, repository.findAll().size());
    }

    @Test
    void shouldThrowMustNotUpdateActiveWorkoutPlanWhenDeleting() throws UnsupportedEncodingException {
        //given
        userWithRole(PERSONAL_TRAINER);
        var entity = repository.save(getSampleActiveWorkoutPlanEntityBuilder().build());

        //when
        var response = performDelete(String.format(DELETE, entity.getId())).getResponse();

        //then
        assertEquals(BAD_REQUEST.value(), response.getStatus());

        //and
        assertTrue(response.getContentAsString().contains(MUST_NOT_CHANGE_ACTIVE_WORKOUT_PLAN.toString()));
    }
}
