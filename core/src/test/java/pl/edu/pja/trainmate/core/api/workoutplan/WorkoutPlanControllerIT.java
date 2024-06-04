package pl.edu.pja.trainmate.core.api.workoutplan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static pl.edu.pja.trainmate.core.api.data.WorkoutPlanSampleData.getSampleWorkoutPlanCreateDtoBuilder;
import static pl.edu.pja.trainmate.core.api.data.WorkoutPlanSampleData.getSampleWorkoutPlanDtoBuilder;
import static pl.edu.pja.trainmate.core.api.data.WorkoutPlanSampleData.getSampleWorkoutPlanEntityBuilder;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.CREATE;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.DELETE;
import static pl.edu.pja.trainmate.core.api.workoutplan.WorkoutPlanEndpoints.UPDATE;
import static pl.edu.pja.trainmate.core.common.ResultStatus.SUCCESS;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.utils.ResponseConverter.castResponseTo;

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
        assertEquals(dto.getDateRange().getFrom(), entity.getDateRange().getFrom());
        assertEquals(dto.getDateRange().getTo(), entity.getDateRange().getTo());
    }

    @Test
    void shouldUpdateWorkoutPlan() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var entity = repository.save(getSampleWorkoutPlanEntityBuilder().build());
        var dto = getSampleWorkoutPlanDtoBuilder()
            .id(entity.getId())
            .name("name2")
            .category("category2")
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
        assertEquals(entity.getDateRange().getFrom(), savedEntity.getDateRange().getFrom());
        assertEquals(entity.getDateRange().getTo(), savedEntity.getDateRange().getTo());
    }

    @Test
    void shouldDeleteWorkoutPlan() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var entity = repository.save(getSampleWorkoutPlanEntityBuilder().build());

        //when
        var response = performDelete(String.format(DELETE, entity.getId())).getResponse();

        //then
        assertEquals(OK.value(), response.getStatus());

        //and
        assertEquals(0, repository.findAll().size());
    }
}
