package pl.edu.pja.trainmate.core.domain.training;

import static pl.edu.pja.trainmate.core.common.error.WorkoutPlanErrorCode.USER_DOES_NOT_HAVE_ACTIVE_WORKOUT_PLAN;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataProvider;
import pl.edu.pja.trainmate.core.domain.exercise.ExerciseReport;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseItemUpdateDto;
import pl.edu.pja.trainmate.core.domain.report.dto.ReportCreateDto;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitDto;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitUpdateDto;
import pl.edu.pja.trainmate.core.domain.training.querydsl.TrainingUnitProjection;
import pl.edu.pja.trainmate.core.domain.workoutplan.WorkoutPlanFacade;

@Service
@RequiredArgsConstructor
public class TrainingUnitFacade {

    private final TrainingUnitService service;
    private final WorkoutPlanFacade workoutPlanFacade;
    private final LoggedUserDataProvider userProvider;

    public List<TrainingUnitProjection> getCurrentTrainingUnits() {
        var userId = userProvider.getLoggedUserId();
        var workoutPlan = Optional.ofNullable(workoutPlanFacade.getCurrentPlan(userId))
            .orElseThrow(() -> new CommonException(USER_DOES_NOT_HAVE_ACTIVE_WORKOUT_PLAN));

        return service.getTrainingUnitsForCurrentWeekForLoggedUser(workoutPlan);
    }

    public List<TrainingUnitProjection> getTrainingUnitsByWorkoutPlanIdAndWeek(Long workoutPlanId, Long week) {
        return service.getTrainingUnitsByWorkoutPlanIdAndWeek(workoutPlanId, week);
    }

    public ExerciseReport getExerciseReport(Long exerciseItemId) {
        return service.getExerciseReportById(exerciseItemId);
    }

    public ResultDto<Long> create(TrainingUnitDto dto) {
        return service.create(dto);
    }

    public void updateTrainingUnit(TrainingUnitUpdateDto dto) {
        service.updateTrainingUnit(dto);
    }

    public void updateExerciseItem(ExerciseItemUpdateDto dto) {
        service.updateExerciseItem(dto);
    }

    public void deleteTrainingUnit(BasicAuditDto dto) {
        service.deleteTrainingUnit(dto);
    }

    public void deleteExerciseItem(BasicAuditDto dto) {
        service.deleteExerciseItem(dto);
    }

    public void addReport(ReportCreateDto reportCreateDto) {
        service.addExerciseItemReport(reportCreateDto);
    }

    public void reviewReport(BasicAuditDto dto) {
        service.reviewReport(dto);
    }
}
