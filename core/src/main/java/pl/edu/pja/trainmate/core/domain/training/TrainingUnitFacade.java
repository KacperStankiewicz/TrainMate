package pl.edu.pja.trainmate.core.domain.training;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitDto;

@Service
@RequiredArgsConstructor
public class TrainingUnitFacade {

    private final TrainingUnitService service;

    public void create(TrainingUnitDto dto) {
        service.create(dto);
    }

    public void updateTrainingUnit(TrainingUnitDto dto) {
        service.updateTrainingUnit(dto);
    }

    public void updateExerciseItem(TrainingUnitDto dto) {
        service.updateExerciseItem(dto);
    }

    public void deleteTrainingUnit(Long id) {
        service.deleteTrainingUnit(id);
    }

    public void deleteExerciseItem(Long id) {
        service.deleteExerciseItem(id);
    }
}
