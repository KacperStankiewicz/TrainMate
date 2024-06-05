package pl.edu.pja.trainmate.core.api.trainingunit;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.DayOfWeek.TUESDAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static pl.edu.pja.trainmate.core.api.data.TrainingUnitSampleData.getSampleExerciseItemEntityBuilder;
import static pl.edu.pja.trainmate.core.api.data.TrainingUnitSampleData.getSampleExerciseItemUpdateDtoBuilder;
import static pl.edu.pja.trainmate.core.api.data.TrainingUnitSampleData.getSampleTrainingUnitDtoBuilder;
import static pl.edu.pja.trainmate.core.api.data.TrainingUnitSampleData.getSampleTrainingUnitEntity;
import static pl.edu.pja.trainmate.core.api.data.TrainingUnitSampleData.getSampleTrainingUnitUpdateDtoBuilder;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.CREATE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.DELETE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.EXERCISE_ITEM_DELETE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.EXERCISE_ITEM_UPDATE;
import static pl.edu.pja.trainmate.core.api.trainingunit.TrainingUnitEndpoints.UPDATE;
import static pl.edu.pja.trainmate.core.common.ResultStatus.SUCCESS;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.utils.ResponseConverter.castResponseTo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseItemRepository;
import pl.edu.pja.trainmate.core.domain.training.TrainingUnitRepository;

@SpringBootTest
@AutoConfigureMockMvc
class TrainingUnitControllerIT extends ControllerSpecification {

    @Autowired
    private TrainingUnitRepository repository;

    @Autowired
    private ExerciseItemRepository exerciseItemRepository;

    @AfterEach
    private void clean() {
        repository.deleteAll();
        exerciseItemRepository.deleteAll();
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
            .build();

        //when
        var response = performPut(String.format(UPDATE, existingEntityId), dto).getResponse();

        //then
        assertEquals(OK.value(), response.getStatus());

        //and
        var entity = repository.findExactlyOneById(existingEntityId);
        var dtoHash = DigestUtils.md5DigestAsHex(
            String.join(";", "1", dto.getWeekNumber().toString(), dto.getDayOfWeek().name()).getBytes(UTF_8));
        assertEquals(dto.getDayOfWeek(), entity.getDayOfWeek());
        assertEquals(dto.getWeekNumber(), entity.getWeekNumber());
        assertEquals(dtoHash, entity.getUniqueHash());
    }

    @Test
    void shouldDeleteTrainingUnit() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var existingEntityId = repository.save(getSampleTrainingUnitEntity()).getId();

        //when
        var response = performDelete(String.format(DELETE, existingEntityId)).getResponse();

        //then
        assertEquals(OK.value(), response.getStatus());

        //and
        assertEquals(0, repository.findAll().size());
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
            .build();

        //when
        var response = performPut(String.format(EXERCISE_ITEM_UPDATE, dto.getId()), dto).getResponse();

        //then
        assertEquals(OK.value(), response.getStatus());

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
    void shouldDeleteTrainingUnitExerciseItem() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var existingEntityId = exerciseItemRepository.save(getSampleExerciseItemEntityBuilder().build()).getId();

        //when
        var response = performDelete(String.format(EXERCISE_ITEM_DELETE, existingEntityId)).getResponse();

        //then
        assertEquals(OK.value(), response.getStatus());

        //and
        assertEquals(0, repository.findAll().size());
    }

}
