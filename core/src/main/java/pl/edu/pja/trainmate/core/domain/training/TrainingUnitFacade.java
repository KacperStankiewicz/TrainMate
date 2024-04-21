package pl.edu.pja.trainmate.core.domain.training;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitCreateDto;
import pl.edu.pja.trainmate.core.domain.training.dto.TrainingUnitDto;

@Service
@RequiredArgsConstructor
public class TrainingUnitFacade {

    private final TrainingUnitService service;

    public void create(TrainingUnitCreateDto dto) {
        service.create(dto);
    }

    public void update(TrainingUnitDto dto) {
        service.update(dto);
    }
}
