package pl.edu.pja.trainmate.core.domain.workoutplan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.AllWorkoutData;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanCreateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanQueryService;

@Service
@RequiredArgsConstructor
public class WorkoutPlanFacade {

    private final WorkoutPlanService service;
    private final WorkoutPlanQueryService queryService;

    public void create(WorkoutPlanCreateDto dto) {
        service.create(dto);
    }

    public void update(WorkoutPlanDto dto) {
        service.update(dto);
    }

    public void delete(Long id) {
        service.delete(id);
    }

    public AllWorkoutData get(Long id) {
        return queryService.getWorkoutPlanData(id);
    }
}
