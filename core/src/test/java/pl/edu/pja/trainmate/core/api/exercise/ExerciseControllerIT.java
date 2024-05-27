package pl.edu.pja.trainmate.core.api.exercise;

import static pl.edu.pja.trainmate.core.api.data.ExerciseSampleData.getCreateDtoBuilder;
import static pl.edu.pja.trainmate.core.api.exercise.ExerciseEndpoints.CREATE;
import static pl.edu.pja.trainmate.core.api.exercise.ExerciseEndpoints.SEARCH;
import static pl.edu.pja.trainmate.core.common.Muscle.GLUTES;
import static pl.edu.pja.trainmate.core.common.Muscle.MIDDLE_CHEST;
import static pl.edu.pja.trainmate.core.common.Muscle.MIDDLE_DELTOID;
import static pl.edu.pja.trainmate.core.common.Muscle.MuscleGroup.LEGS;
import static pl.edu.pja.trainmate.core.common.ResultStatus.SUCCESS;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.utils.ResponseConverter.castResponseTo;
import static pl.edu.pja.trainmate.core.utils.ResponseConverter.castResponseToPage;

import io.vavr.collection.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseEntity;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseRepository;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseListItemProjection;
import pl.edu.pja.trainmate.core.domain.exercise.querydsl.ExerciseSearchCriteria;

@SpringBootTest
@AutoConfigureMockMvc
class ExerciseControllerIT extends ControllerSpecification {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Test
    void shouldCreateExercise() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var createDto = getCreateDtoBuilder().build();

        //when
        var response = performPost(CREATE, createDto).getResponse();

        //then
        var responseBody = castResponseTo(response, ResultDto.class);
        Assert.assertEquals(SUCCESS, responseBody.getStatus());

        //and
        var entity = exerciseRepository.findExactlyOneById((Long) responseBody.getValue());
        Assert.assertEquals(createDto.getDescription(), entity.getDescription());
        Assert.assertEquals(createDto.getUrl(), entity.getUrl());
        Assert.assertEquals(createDto.getName(), entity.getName());
        Assert.assertEquals(createDto.getMuscleInvolved(), entity.getMuscleInvolved());
    }

    @Test
    void shouldSearchExercisesByMuscleGroupCriteria() {
        //given
        userWithRole(PERSONAL_TRAINER);
        var firstExercise = ExerciseEntity.builder()
            .name("first")
            .description("first desc")
            .url("www.google.com")
            .muscleInvolved(MIDDLE_CHEST)
            .build();

        var secondExercise = ExerciseEntity.builder()
            .name("second")
            .description("third desc")
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

        var criteria = ExerciseSearchCriteria.builder()
            .muscleGroup(LEGS)
            .build();

        //when
        var response = performPost(SEARCH, criteria).getResponse();

        //then
        var result = castResponseToPage(response, ExerciseListItemProjection.class);
        Assert.assertEquals(1, result.getTotalElements());
        var entity = result.getContent().get(0);
        Assert.assertEquals(thirdExercise.getMuscleInvolved(), entity.getMuscleInvolved());
        Assert.assertEquals(thirdExercise.getName(), entity.getName());
    }


}
