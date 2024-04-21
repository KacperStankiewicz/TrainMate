package pl.edu.pja.trainmate.core.domain.workoutplan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanCreateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanDto;

@Service
@RequiredArgsConstructor
public class WorkoutPlanFacade {

        private final WorkoutPlanService service;

        public void create(WorkoutPlanCreateDto dto) {
            service.create(dto);
        }

        public void update(WorkoutPlanDto dto) {
            service.update(dto);
        }
}
