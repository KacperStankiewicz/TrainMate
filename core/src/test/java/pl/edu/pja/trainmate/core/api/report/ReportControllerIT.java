package pl.edu.pja.trainmate.core.api.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.EXERCISE_REPORT;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.EXERCISE_REPORT_REVIEW;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.WORKOUT_PLAN_REPORT;
import static pl.edu.pja.trainmate.core.api.report.ReportEndpoints.WORKOUT_PLAN_REPORT_REVIEW;
import static pl.edu.pja.trainmate.core.api.sampledata.ReportSampleData.getExerciseReportBuilder;
import static pl.edu.pja.trainmate.core.api.sampledata.ReportSampleData.getExerciseReportSampleDataBuilder;
import static pl.edu.pja.trainmate.core.api.sampledata.ReportSampleData.getSamplePeriodicalReportCreateDtoBuilder;
import static pl.edu.pja.trainmate.core.common.ResultStatus.SUCCESS;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.config.security.RoleType.MENTEE;
import static pl.edu.pja.trainmate.core.testutils.ResponseConverter.castResponseTo;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pja.trainmate.core.ControllerSpecification;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
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
        userWithRole(MENTEE);
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
        var dto = BasicAuditDto.ofValue(entity.getId(), entity.getVersion());
        userWithRole(PERSONAL_TRAINER);

        //when
        var response = performPost(String.format(WORKOUT_PLAN_REPORT_REVIEW, entity.getId()), dto).getResponse();

        //then
        assertEquals(200, response.getStatus());
        assertTrue(reportRepository.findExactlyOneById(entity.getId()).isReviewed());
    }

    @Test
    @SneakyThrows
    void shouldThrowOptimisticLockExceptionWhenMarkingReportAsReviewed() {
        //given
        var entity = reportRepository.save(ReportEntity.builder().build());
        var dto = BasicAuditDto.ofValue(entity.getId(), 9999L);
        userWithRole(PERSONAL_TRAINER);

        //when
        var response = performPost(String.format(WORKOUT_PLAN_REPORT_REVIEW, entity.getId()), dto).getResponse();

        //then
        assertEquals(409, response.getStatus());
        assertTrue(response.getContentAsString().contains("RESOURCE_HAS_BEEN_MODIFIED_BY_ANOTHER_USER"));
        assertFalse(reportRepository.findExactlyOneById(entity.getId()).isReviewed());
    }

    @Test
    void shouldCreateExerciseItemReport() {
        //given
        var exercise = exerciseItemRepository.save(ExerciseItemEntity.builder().build());
        var dto = getExerciseReportSampleDataBuilder()
            .exerciseItemId(exercise.getId())
            .build();
        userWithRole(MENTEE);

        //when
        var response = performPost(String.format(EXERCISE_REPORT, dto.getExerciseItemId()), dto).getResponse();

        //then
        assertEquals(200, response.getStatus());

        //and
        var entity = exerciseItemRepository.findExactlyOneById(exercise.getId());
        var savedSet = entity.getExerciseReport().getReportedSets().get(0);
        var dtoSet = dto.getSets().get(0);
        assertEquals(dtoSet.getReportedRepetitions(), savedSet.getReportedRepetitions());
        assertEquals(dtoSet.getReportedWeight(), savedSet.getReportedWeight());
        assertEquals(dtoSet.getReportedRir(), savedSet.getReportedRir());
        assertEquals(dto.getSets().size(), entity.getExerciseReport().getReportedSets().size());
        assertEquals(dto.getRemarks(), entity.getExerciseReport().getRemarks());
        assertTrue(entity.getReported());
    }

    @Test
    void shouldMarkExerciseReportAsReviewed() {
        //given
        var report = getExerciseReportBuilder().build();
        var exercise = exerciseItemRepository.save(ExerciseItemEntity.builder()
            .exerciseReport(report)
            .reported(true)
            .build());
        var dto = BasicAuditDto.ofValue(exercise.getId(), exercise.getVersion());
        userWithRole(PERSONAL_TRAINER);

        //when
        var response = performPost(String.format(EXERCISE_REPORT_REVIEW, exercise.getId()), dto).getResponse();

        //then
        assertEquals(200, response.getStatus());
        assertTrue(exerciseItemRepository.findExactlyOneById(exercise.getId()).getExerciseReport().isReviewed());
    }

    @Test
    @SneakyThrows
    void shouldThrowOptimisticLockExceptionWhenReviewingExerciseItemReport() {
        //given
        var report = getExerciseReportBuilder().build();
        var exercise = exerciseItemRepository.save(ExerciseItemEntity.builder()
            .exerciseReport(report)
            .reported(true)
            .build());
        var dto = BasicAuditDto.ofValue(exercise.getId(), 99999L);
        userWithRole(PERSONAL_TRAINER);

        //when
        var response = performPost(String.format(EXERCISE_REPORT_REVIEW, exercise.getId()), dto).getResponse();

        //then
        assertEquals(409, response.getStatus());
        assertTrue(response.getContentAsString().contains("RESOURCE_HAS_BEEN_MODIFIED_BY_ANOTHER_USER"));
        assertFalse(exerciseItemRepository.findExactlyOneById(exercise.getId()).getExerciseReport().isReviewed());
    }

}
