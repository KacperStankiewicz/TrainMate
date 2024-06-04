package pl.edu.pja.trainmate.core.api.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.edu.pja.trainmate.core.api.data.ReportSampleData.getExerciseReportBuilder;
import static pl.edu.pja.trainmate.core.api.data.ReportSampleData.getExerciseReportSampleDataBuilder;
import static pl.edu.pja.trainmate.core.api.data.ReportSampleData.getSamplePeriodicalReportCreateDtoBuilder;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.EXERCISE_REPORT;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.EXERCISE_REPORT_REVIEW;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.WORKOUT_PLAN_REPORT;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.WORKOUT_PLAN_REPORT_REVIEW;
import static pl.edu.pja.trainmate.core.common.ResultStatus.SUCCESS;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;
import static pl.edu.pja.trainmate.core.utils.ResponseConverter.castResponseTo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseItemEntity;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseItemRepository;
import pl.edu.pja.trainmate.core.domain.report.ReportEntity;
import pl.edu.pja.trainmate.core.domain.report.ReportRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ReportControllerIT extends ControllerSpecification {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ExerciseItemRepository exerciseItemRepository;

    @AfterEach
    private void clean() {
        reportRepository.deleteAll();
    }

    @Test
    void shouldCreatePeriodicalReport() {
        //given
        userWithRole(TRAINED_PERSON);
        var dto = getSamplePeriodicalReportCreateDtoBuilder().build();

        //when
        var response = performPost(String.format(WORKOUT_PLAN_REPORT, dto.getWorkoutPlanId()), dto).getResponse();

        //then
        var responseBody = castResponseTo(response, ResultDto.class);
        assertEquals(SUCCESS, responseBody.getStatus());

        //and
        var entity = reportRepository.findExactlyOneById((Long) responseBody.getValue());
        assertEquals(dto.getBodyFat(), entity.getBodyFat());
        assertEquals(dto.getWeight(), entity.getWeight());
        assertEquals(dto.getHips(), entity.getCircumferences().getHips());
        assertFalse(entity.isInitial());
        assertFalse(entity.isReviewed());
    }

    @Test
    void shouldMarkReportAsReviewed() {
        //given
        var entity = reportRepository.save(ReportEntity.builder().build());
        userWithRole(PERSONAL_TRAINER);

        //when
        var response = performPost(String.format(WORKOUT_PLAN_REPORT_REVIEW, entity.getId())).getResponse();

        //then
        assertEquals(200, response.getStatus());
        assertTrue(reportRepository.findExactlyOneById(entity.getId()).isReviewed());

    }

    @Test
    void shouldCreateExerciseItemReport() {
        //given
        var exercise = exerciseItemRepository.save(ExerciseItemEntity.builder().build());
        var dto = getExerciseReportSampleDataBuilder()
            .exerciseItemId(exercise.getId())
            .build();
        userWithRole(TRAINED_PERSON);

        //when
        var response = performPost(String.format(EXERCISE_REPORT, dto.getExerciseItemId()), dto).getResponse();

        //then
        assertEquals(200, response.getStatus());

        //and
        var entity = exerciseItemRepository.findExactlyOneById(exercise.getId());
        assertEquals(dto.getRepetitions(), entity.getExerciseReport().getReportedRepetitions());
        assertEquals(dto.getWeight(), entity.getExerciseReport().getReportedWeight());
        assertEquals(dto.getSets(), entity.getExerciseReport().getReportedSets());
        assertEquals(dto.getRir(), entity.getExerciseReport().getReportedRir());
        assertEquals(dto.getRemarks(), entity.getExerciseReport().getRemarks());
        assertTrue(entity.isReported());
    }

    @Test
    void shouldMarkExerciseReportAsReviewed() {
        //given
        var report = getExerciseReportBuilder().build();
        var exercise = exerciseItemRepository.save(ExerciseItemEntity.builder()
            .exerciseReport(report)
                .reported(true)
            .build());
        userWithRole(PERSONAL_TRAINER);

        //when
        var response = performPost(String.format(EXERCISE_REPORT_REVIEW, exercise.getId())).getResponse();

        //then
        assertEquals(200, response.getStatus());
        assertTrue(exerciseItemRepository.findExactlyOneById(exercise.getId()).getExerciseReport().isReviewed());

    }

}
