package pl.edu.pja.trainmate.core.domain.workoutplan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.AllWorkoutData;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanCreateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanUpdateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanProjection;

@Service
@RequiredArgsConstructor
public class WorkoutPlanFacade {

    private final WorkoutPlanService service;

    public AllWorkoutData getById(Long id) {
        return service.getWorkoutPlanData(id);
    }

    public WorkoutPlanProjection getCurrentPlan(UserId userId) {
        return service.getCurrentWorkoutPlanProjection(userId);
    }

    public ResultDto<Long> create(WorkoutPlanCreateDto dto) {
        return service.create(dto);
    }

    public void update(WorkoutPlanUpdateDto dto) {
        service.update(dto);
    }

    public void delete(BasicAuditDto dto) {
        service.delete(dto);
    }
}
