package pl.edu.pja.trainmate.core.api.trainingunit;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.TUESDAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.edu.pja.trainmate.core.api.sampledata.ExerciseSampleData.getExerciseEntityBuilder;
import static pl.edu.pja.trainmate.core.api.sampledata.TrainingUnitSampleData.getSampleExerciseItemEntityBuilder;
import static pl.edu.pja.trainmate.core.api.sampledata.TrainingUnitSampleData.getSampleExerciseItemUpdateDtoBuilder;
import static pl.edu.pja.trainmate.core.api.sampledata.TrainingUnitSampleData.getSampleTrainingUnitDtoBuilder;
import static pl.edu.pja.trainmate.core.api.sampledata.TrainingUnitSampleData.getSampleTrainingUnitEntity;
import static pl.edu.pja.trainmate.core.api.sampledata.TrainingUnitSampleData.getSampleTrainingUnitEntityBuilder;
import static pl.edu.pja.trainmate.core.api.sampledata.TrainingUnitSampleData.getSampleTrainingUnitUpdateDtoBuilder;
import static pl.edu.pja.trainmate.core.api.sampledata.WorkoutPlanSampleData.getSampleActiveWorkoutPlanEntityBuilder;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.CREATE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.DELETE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.EXERCISE_ITEM_DELETE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.EXERCISE_ITEM_UPDATE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.GET_FOR_CURRENT_WEEK;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.GET_FOR_WEEK;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.UPDATE;
import static pl.edu.pja.trainmate.core.common.ResultStatus.SUCCESS;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;
import static pl.edu.pja.trainmate.core.testutils.ResponseConverter.castResponseTo;
import static pl.edu.pja.trainmate.core.testutils.ResponseConverter.castResponseToList;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseItemRepository;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseRepository;
import pl.edu.pja.trainmate.core.domain.training.TrainingUnitRepository;
import pl.edu.pja.trainmate.core.domain.training.querydsl.TrainingUnitProjection;
import pl.edu.pja.trainmate.core.domain.workoutplan.WorkoutPlanRepository;

@SpringBootTest
@AutoConfigureMockMvc
class TrainingUnitControllerIT extends ControllerSpecification {

    @Autowired
    private TrainingUnitRepository repository;

    @Autowired
    private ExerciseItemRepository exerciseItemRepository;

    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    private Long existingWorkoutPlanId;

    @AfterEach
    private void clean() {
        repository.deleteAll();
        exerciseItemRepository.deleteAll();
        workoutPlanRepository.deleteAll();
        exerciseRepository.deleteAll();
    }

    @Test
    void shouldCreateTrainingUnit() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var dto = getSampleTrainingUnitDtoBuilder()
            .id(null)
            .build();

        //when
        var response = performPost(CREATE, dto).getResponse();

        //then
        var responseBody = castResponseTo(response, ResultDto.class);
        assertEquals(SUCCESS, responseBody.getStatus());

        //and
        var entity = repository.findExactlyOneById((Long) responseBody.getValue());
        var volume = exerciseItemRepository.getExerciseItemEntityByTrainingUnitId(entity.getId()).getVolume();
        var dtoHash = DigestUtils.md5DigestAsHex(
            String.join(";", dto.getWorkoutPlanId().toString(), dto.getWeekNumber().toString(), dto.getDayOfWeek().name()).getBytes(UTF_8));
        assertEquals(dto.getDayOfWeek(), entity.getDayOfWeek());
        assertEquals(dto.getWeekNumber(), entity.getWeekNumber());
        assertEquals(dto.getRepetitions(), volume.getRepetitions());
        assertEquals(dto.getRir(), volume.getRir());
        assertEquals(dto.getTempo(), volume.getTempo());
        assertEquals(dto.getSets(), volume.getSets());
        assertEquals(dto.getWeight(), volume.getWeight());
        assertEquals(dtoHash, entity.getUniqueHash());
    }

    @Test
    void shouldUpdateTrainingUnit() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var existingEntityId = repository.save(getSampleTrainingUnitEntity()).getId();
        var dto = getSampleTrainingUnitUpdateDtoBuilder()
            .id(existingEntityId)
            .weekNumber(2L)
            .dayOfWeek(TUESDAY)
            .version(getTrainingUnitActualVersion(existingEntityId))
            .build();

        //when
        var response = performPut(String.format(UPDATE, existingEntityId), dto).getResponse();

        //then
        assertEquals(200, response.getStatus());

        //and
        var entity = repository.findExactlyOneById(existingEntityId);
        var dtoHash = DigestUtils.md5DigestAsHex(
            String.join(";", "1", dto.getWeekNumber().toString(), dto.getDayOfWeek().name()).getBytes(UTF_8));
        assertEquals(dto.getDayOfWeek(), entity.getDayOfWeek());
        assertEquals(dto.getWeekNumber(), entity.getWeekNumber());
        assertEquals(dtoHash, entity.getUniqueHash());
    }

    @SneakyThrows
    @Test
    void shouldThrowOptimisticLockExceptionWhenUpdatingTrainingUnit() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var existingEntityId = repository.save(getSampleTrainingUnitEntity()).getId();
        var dto = getSampleTrainingUnitUpdateDtoBuilder()
            .id(existingEntityId)
            .weekNumber(2L)
            .dayOfWeek(TUESDAY)
            .version(9999L)
            .build();

        //when
        var response = performPut(String.format(UPDATE, existingEntityId), dto).getResponse();

        //then
        assertEquals(409, response.getStatus());
        assertTrue(response.getContentAsString().contains("RESOURCE_HAS_BEEN_MODIFIED_BY_ANOTHER_USER"));
        //and
        var entity = repository.findExactlyOneById(existingEntityId);
        var dtoHash = DigestUtils.md5DigestAsHex(
            String.join(";", "1", dto.getWeekNumber().toString(), dto.getDayOfWeek().name()).getBytes(UTF_8));
        assertNotEquals(dto.getDayOfWeek(), entity.getDayOfWeek());
        assertNotEquals(dto.getWeekNumber(), entity.getWeekNumber());
        assertNotEquals(dtoHash, entity.getUniqueHash());
    }

    @Test
    void shouldDeleteTrainingUnit() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var existingEntity = repository.save(getSampleTrainingUnitEntity());
        var dto = BasicAuditDto.ofValue(existingEntity.getId(), existingEntity.getVersion());

        //when
        var response = performDelete(String.format(DELETE, existingEntity.getId()), dto).getResponse();

        //then
        assertEquals(200, response.getStatus());

        //and
        assertEquals(0, repository.findAll().size());
    }

    @Test
    @SneakyThrows
    void shouldThrowOptimisticLockExceptionWhenDeletingTrainingUnit() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var existingEntity = repository.save(getSampleTrainingUnitEntity());
        var dto = BasicAuditDto.ofValue(existingEntity.getId(), 99999L);

        //when
        var response = performDelete(String.format(DELETE, existingEntity.getId()), dto).getResponse();

        //then
        assertEquals(409, response.getStatus());
        assertTrue(response.getContentAsString().contains("RESOURCE_HAS_BEEN_MODIFIED_BY_ANOTHER_USER"));

        //and
        assertNotEquals(0, repository.findAll().size());
    }

    @Test
    void shouldUpdateTrainingUnitsExerciseItem() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var responseBody = castResponseTo(
            performPost(
                CREATE,
                getSampleTrainingUnitDtoBuilder().id(null).build()
            ).getResponse(),
            ResultDto.class
        );

        var exerciseItemId = exerciseItemRepository.getExerciseItemEntityByTrainingUnitId((Long) responseBody.getValue()).getId();
        var dto = getSampleExerciseItemUpdateDtoBuilder()
            .id(exerciseItemId)
            .trainingUnitId((Long) responseBody.getValue())
            .version(getExerciseItemActualVersion(exerciseItemId))
            .build();

        //when
        var response = performPut(String.format(EXERCISE_ITEM_UPDATE, dto.getId()), dto).getResponse();

        //then
        assertEquals(200, response.getStatus());

        //and
        var entity = exerciseItemRepository.findExactlyOneById(dto.getId());
        assertEquals(dto.getRepetitions(), entity.getVolume().getRepetitions());
        assertEquals(dto.getRir(), entity.getVolume().getRir());
        assertEquals(dto.getTempo(), entity.getVolume().getTempo());
        assertEquals(dto.getSets(), entity.getVolume().getSets());
        assertEquals(dto.getWeight(), entity.getVolume().getWeight());
        assertEquals(dto.getExerciseId(), entity.getExerciseId());
        assertEquals(dto.getTrainingUnitId(), entity.getTrainingUnitId());
    }

    @Test
    @SneakyThrows
    void shouldThrowOptimisticLockExceptionWhenUpdatingTrainingUnitsExerciseItem() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var responseBody = castResponseTo(
            performPost(
                CREATE,
                getSampleTrainingUnitDtoBuilder().id(null).build()
            ).getResponse(),
            ResultDto.class
        );

        var exerciseItemId = exerciseItemRepository.getExerciseItemEntityByTrainingUnitId((Long) responseBody.getValue()).getId();
        var dto = getSampleExerciseItemUpdateDtoBuilder()
            .id(exerciseItemId)
            .trainingUnitId((Long) responseBody.getValue())
            .version(9999L)
            .build();

        //when
        var response = performPut(String.format(EXERCISE_ITEM_UPDATE, dto.getId()), dto).getResponse();

        //then
        assertEquals(409, response.getStatus());
        assertTrue(response.getContentAsString().contains("RESOURCE_HAS_BEEN_MODIFIED_BY_ANOTHER_USER"));

        //and
        var entity = exerciseItemRepository.findExactlyOneById(dto.getId());
        assertNotEquals(dto.getRepetitions(), entity.getVolume().getRepetitions());
        assertNotEquals(dto.getRir(), entity.getVolume().getRir());
        assertNotEquals(dto.getTempo(), entity.getVolume().getTempo());
        assertNotEquals(dto.getSets(), entity.getVolume().getSets());
        assertNotEquals(dto.getWeight(), entity.getVolume().getWeight());
        assertNotEquals(dto.getExerciseId(), entity.getExerciseId());
    }

    @Test
    void shouldDeleteTrainingUnitExerciseItem() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var existingEntity = exerciseItemRepository.save(getSampleExerciseItemEntityBuilder().build());
        var dto = BasicAuditDto.ofValue(existingEntity.getId(), existingEntity.getVersion());

        //when
        var response = performDelete(String.format(EXERCISE_ITEM_DELETE, dto.getId()), dto).getResponse();

        //then
        assertEquals(200, response.getStatus());

        //and
        assertEquals(0, exerciseRepository.findAll().size());
    }

    @Test
    @SneakyThrows
    void shouldThrowOptimisticLockExceptionWhenDeletingTrainingUnitExerciseItem() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var existingEntity = exerciseItemRepository.save(getSampleExerciseItemEntityBuilder().build());
        var dto = BasicAuditDto.ofValue(existingEntity.getId(), 9999L);

        //when
        var response = performDelete(String.format(EXERCISE_ITEM_DELETE, dto.getId()), dto).getResponse();

        //then
        assertEquals(409, response.getStatus());
        assertTrue(response.getContentAsString().contains("RESOURCE_HAS_BEEN_MODIFIED_BY_ANOTHER_USER"));

        //and
        assertNotEquals(0, exerciseItemRepository.findAll().size());
    }

    @Test
    void shouldGetTrainingUnitsForCurrentWeek() {
        //given
        userWithRole(TRAINED_PERSON);
        createWorkoutPlanWithTraining();

        //when
        var response = performGet(GET_FOR_CURRENT_WEEK).getResponse();

        //then
        assertEquals(200, response.getStatus());

        //and
        var responseBody = castResponseToList(response, TrainingUnitProjection.class);

        assertEquals(1, responseBody.size());
        var first = responseBody.get(0);

        assertEquals(MONDAY, first.getDayOfWeek());
        assertEquals(1L, first.getWeekNumber());
        assertEquals(1, first.getExercises().size());

        var firstExercise = first.getExercises().get(0);
        assertEquals("Bench Press", firstExercise.getName());
        assertEquals(1, firstExercise.getSets());
    }

    @Test
    void shouldGetTrainingUnitsForGivenWorkoutPlanIdAndWeekNumber() {
        //given
        userWithRole(PERSONAL_TRAINER);
        createWorkoutPlanWithTraining();

        var secondTrainingUnit = getSampleTrainingUnitEntityBuilder()
            .workoutPlanId(1L)
            .weekNumber(2L)
            .build();
        secondTrainingUnit.calculateHash();
        repository.save(secondTrainingUnit);

        //when
        var response = performGet(String.format(GET_FOR_WEEK, existingWorkoutPlanId, 1L)).getResponse();

        //then
        assertEquals(200, response.getStatus());

        //and
        var responseBody = castResponseToList(response, TrainingUnitProjection.class);

        assertEquals(1, responseBody.size());
        var first = responseBody.get(0);

        assertEquals(MONDAY, first.getDayOfWeek());
        assertEquals(1L, first.getWeekNumber());
        assertEquals(1, first.getExercises().size());

        var firstExercise = first.getExercises().get(0);
        assertEquals("Bench Press", firstExercise.getName());
        assertEquals(1, firstExercise.getSets());
    }

    private void createWorkoutPlanWithTraining() {
        var exercise = exerciseRepository.save(getExerciseEntityBuilder().build());
        var workoutPlan = workoutPlanRepository.save(
            getSampleActiveWorkoutPlanEntityBuilder()
                .userId(EXISTING_USER_ID)
                .build()
        );
        existingWorkoutPlanId = workoutPlan.getId();
        var trainingUnit = repository.save(
            getSampleTrainingUnitEntityBuilder()
                .workoutPlanId(workoutPlan.getId())
                .build()
        );

        exerciseItemRepository.save(
            getSampleExerciseItemEntityBuilder()
                .exerciseId(exercise.getId())
                .trainingUnitId(trainingUnit.getId())
                .workoutPlanId(workoutPlan.getId())
                .build()
        );
    }

    Long getExerciseItemActualVersion(Long id) {
        return exerciseItemRepository.findExactlyOneById(id).getVersion();
    }

    Long getTrainingUnitActualVersion(Long id) {
        return repository.findExactlyOneById(id).getVersion();
    }
}
