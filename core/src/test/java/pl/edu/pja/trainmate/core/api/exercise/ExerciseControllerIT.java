package pl.edu.pja.trainmate.core.api.exercise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.edu.pja.trainmate.core.api.exercise.ExerciseEndpoints.CREATE;
import static pl.edu.pja.trainmate.core.api.exercise.ExerciseEndpoints.DELETE;
import static pl.edu.pja.trainmate.core.api.exercise.ExerciseEndpoints.GET;
import static pl.edu.pja.trainmate.core.api.exercise.ExerciseEndpoints.SEARCH;
import static pl.edu.pja.trainmate.core.api.exercise.ExerciseEndpoints.UPDATE;
import static pl.edu.pja.trainmate.core.api.sampledata.ExerciseSampleData.getCreateDtoBuilder;
import static pl.edu.pja.trainmate.core.api.sampledata.ExerciseSampleData.getExerciseDtoBuilder;
import static pl.edu.pja.trainmate.core.common.Muscle.BICEPS;
import static pl.edu.pja.trainmate.core.common.Muscle.GLUTES;
import static pl.edu.pja.trainmate.core.common.Muscle.MIDDLE_CHEST;
import static pl.edu.pja.trainmate.core.common.Muscle.MIDDLE_DELTOID;
import static pl.edu.pja.trainmate.core.common.Muscle.MuscleGroup.LEGS;
import static pl.edu.pja.trainmate.core.common.ResultStatus.SUCCESS;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.testutils.ResponseConverter.castResponseTo;
import static pl.edu.pja.trainmate.core.testutils.ResponseConverter.castResponseToPage;

import io.vavr.collection.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseEntity;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseRepository;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseListItemProjection;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseProjection;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.ExerciseSearchCriteria;

@SpringBootTest
@AutoConfigureMockMvc
class ExerciseControllerIT extends ControllerSpecification {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @AfterEach
    private void clean() {
        exerciseRepository.deleteAll();
    }

    @Test
    void shouldCreateAndGetExercise() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var createDto = getCreateDtoBuilder().build();

        //when
        var response = performPost(CREATE, createDto).getResponse();

        //then
        var responseBody = castResponseTo(response, ResultDto.class);
        assertEquals(SUCCESS, responseBody.getStatus());

        //and
        var getResponse = performGet(String.format(GET, responseBody.getValue())).getResponse();
        var exercise = castResponseTo(getResponse, ExerciseProjection.class);
        assertEquals(createDto.getDescription(), exercise.getDescription());
        assertEquals(createDto.getUrl(), exercise.getUrl());
        assertEquals(createDto.getName(), exercise.getName());
        assertEquals(createDto.getMuscleInvolved(), exercise.getMuscleInvolved());
    }

    @Test
    void shouldUpdateExercise() {
        //given
        var entity = ExerciseEntity.builder()
            .name("name")
            .description("desc")
            .url("url")
            .muscleInvolved(BICEPS)
            .build();
        entity = exerciseRepository.save(entity);

        userWithRole(PERSONAL_TRAINER);
        var updateDto = getExerciseDtoBuilder()
            .id(entity.getId())
            .version(entity.getVersion())
            .build();

        //when
        var response = performPut(String.format(UPDATE, entity.getId()), updateDto).getResponse();

        //then
        assertEquals(200, response.getStatus());

        //and
        var updated = exerciseRepository.findExactlyOneById(entity.getId());
        assertEquals(updateDto.getName(), updated.getName());
        assertEquals(updateDto.getUrl(), updated.getUrl());
        assertEquals(updateDto.getDescription(), updated.getDescription());
        assertEquals(updateDto.getMuscleInvolved(), updated.getMuscleInvolved());
    }

    @Test
    @SneakyThrows
    void shouldThrowOptimisticLockExceptionWhenUpdatingExercise() {
        //given
        var entity = ExerciseEntity.builder()
            .name("name")
            .description("desc")
            .url("urlurl")
            .muscleInvolved(BICEPS)
            .build();
        entity = exerciseRepository.save(entity);

        userWithRole(PERSONAL_TRAINER);
        var updateDto = getExerciseDtoBuilder()
            .id(entity.getId())
            .version(9999L)
            .build();

        //when
        var response = performPut(String.format(UPDATE, entity.getId()), updateDto).getResponse();

        //then
        assertEquals(409, response.getStatus());
        assertTrue(response.getContentAsString().contains("RESOURCE_HAS_BEEN_MODIFIED_BY_ANOTHER_USER"));
        //and
        var updated = exerciseRepository.findExactlyOneById(entity.getId());
        assertNotEquals(updateDto.getName(), updated.getName());
        assertNotEquals(updateDto.getUrl(), updated.getUrl());
        assertNotEquals(updateDto.getDescription(), updated.getDescription());
        assertNotEquals(updateDto.getMuscleInvolved(), updated.getMuscleInvolved());
    }

    @Test
    void shouldDeleteExercise() {
        //given
        var entity = ExerciseEntity.builder()
            .name("name")
            .description("desc")
            .url("url")
            .muscleInvolved(BICEPS)
            .build();
        entity = exerciseRepository.save(entity);
        var dto = BasicAuditDto.ofValue(entity.getId(), entity.getVersion());
        userWithRole(PERSONAL_TRAINER);

        //when
        var response = performDelete(String.format(DELETE, entity.getId()), dto).getResponse();

        //then
        assertEquals(200, response.getStatus());

        //and
        assertEquals(0, exerciseRepository.findAll().size());
    }

    @Test
    @SneakyThrows
    void shouldThrowOptimisticLockExceptionWhenDeletingExercise() {
        //given
        var entity = ExerciseEntity.builder()
            .name("name")
            .description("desc")
            .url("url")
            .muscleInvolved(BICEPS)
            .build();
        entity = exerciseRepository.save(entity);
        var dto = BasicAuditDto.ofValue(entity.getId(), 999L);
        userWithRole(PERSONAL_TRAINER);

        //when
        var response = performDelete(String.format(DELETE, entity.getId()), dto).getResponse();

        //then
        assertEquals(409, response.getStatus());

        //and
        assertNotEquals(0, exerciseRepository.findAll().size());
        assertTrue(response.getContentAsString().contains("RESOURCE_HAS_BEEN_MODIFIED_BY_ANOTHER_USER"));
    }

    @Test
    void shouldSearchExercisesByMuscleGroupCriteria() {
        //given
        createSampleEntities();
        var criteria = ExerciseSearchCriteria.builder()
            .muscleGroup(LEGS)
            .build();
        userWithRole(PERSONAL_TRAINER);

        //when
        var response = performPost(SEARCH, criteria).getResponse();

        //then
        var result = castResponseToPage(response, ExerciseListItemProjection.class);
        assertEquals(1, result.getTotalElements());
        var entity = result.getContent().get(0);
        assertEquals(GLUTES, entity.getMuscleInvolved());
        assertEquals("third", entity.getName());
    }

    @Test
    void shouldSearchExercisesByNameCriteria() {
        //given
        createSampleEntities();
        var criteria = ExerciseSearchCriteria.builder()
            .name("first")
            .build();
        userWithRole(PERSONAL_TRAINER);

        //when
        var response = performPost(SEARCH, criteria).getResponse();

        //then
        var result = castResponseToPage(response, ExerciseListItemProjection.class);
        assertEquals(1, result.getTotalElements());
        var entity = result.getContent().get(0);
        assertEquals(MIDDLE_CHEST, entity.getMuscleInvolved());
        assertEquals("first", entity.getName());
    }

    @Test
    void shouldSearchAllExercisesWhenEmptyCriteria() {
        //given
        createSampleEntities();
        var criteria = ExerciseSearchCriteria.builder()
            .build();
        userWithRole(PERSONAL_TRAINER);

        //when
        var response = performPost(SEARCH, criteria).getResponse();

        //then
        var result = castResponseToPage(response, ExerciseListItemProjection.class);
        assertEquals(3, result.getTotalElements());
    }

    private void createSampleEntities() {
        var firstExercise = ExerciseEntity.builder()
            .name("first")
            .description("first desc")
            .url("www.google.com")
            .muscleInvolved(MIDDLE_CHEST)
            .build();

        var secondExercise = ExerciseEntity.builder()
            .name("second")
            .description("second desc")
            .url("www.google.com")
            .muscleInvolved(MIDDLE_DELTOID)
            .build();

        var thirdExercise = ExerciseEntity.builder()
            .name("third")
            .description("third desc")
            .url("www.google.com")
            .muscleInvolved(GLUTES)
            .build();

        exerciseRepository.saveAll(List.of(firstExercise, secondExercise, thirdExercise));
    }
}
