package pl.edu.pja.trainmate.core.domain.workoutplan;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BasicAuditDto;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataProvider;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.AllWorkoutData;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanCreateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.dto.WorkoutPlanUpdateDto;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanListItemProjection;
import pl.edu.pja.trainmate.core.domain.workoutplan.querydsl.WorkoutPlanProjection;

@Service
@RequiredArgsConstructor
public class WorkoutPlanFacade {

    private final WorkoutPlanService service;
    private final LoggedUserDataProvider userProvider;

    public AllWorkoutData getById(Long id) {
        return service.getWorkoutPlanData(id);
    }

    public WorkoutPlanProjection getWorkoutPlanHeader(Long workoutPlanId) {
        return service.getWorkoutPlanHeader(workoutPlanId);
    }

    public List<WorkoutPlanListItemProjection> getAllPlansByUserId(String keycloakId) {
        return service.getAllWorkouPlansByUserId(UserId.valueOf(keycloakId));
    }

    public List<WorkoutPlanListItemProjection> getAllPlansForLoggedUser() {
        return service.getAllWorkouPlansByUserId(userProvider.getLoggedUserId());
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

    public WorkoutPlanProjection getCurrentPlan(UserId userId) {
        return service.getCurrentWorkoutPlanProjection(userId);
    }
}
