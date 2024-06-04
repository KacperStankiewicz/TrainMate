package pl.edu.pja.trainmate.core.domain.training;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.domain.exercise.dto.ExerciseItemUpdateDto;
import pl.edu.pja.trainmate.core.domain.report.dto.ReportCreateDto;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitDto;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitUpdateDto;

@Service
@RequiredArgsConstructor
public class TrainingUnitFacade {

    private final TrainingUnitService service;

    public ResultDto<Long> create(TrainingUnitDto dto) {
        return service.create(dto);
    }

    public void updateTrainingUnit(TrainingUnitUpdateDto dto) {
        service.updateTrainingUnit(dto);
    }

    public void updateExerciseItem(ExerciseItemUpdateDto dto) {
        service.updateExerciseItem(dto);
    }

    public void deleteTrainingUnit(Long id) {
        service.deleteTrainingUnit(id);
    }

    public void deleteExerciseItem(Long id) {
        service.deleteExerciseItem(id);
    }

    public void addReport(ReportCreateDto reportCreateDto) {
        service.addExerciseItemReport(reportCreateDto);
    }

    public void reviewReport(Long exerciseItemId) {
        service.reviewReport(exerciseItemId);
    }
}
