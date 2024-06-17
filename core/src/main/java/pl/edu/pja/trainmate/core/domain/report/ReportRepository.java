package pl.edu.pja.trainmate.core.domain.report;

import org.springframework.stereotype.Repository;
import pl.edu.pja.trainmate.core.common.BaseRepository;
import pl.edu.pja.trainmate.core.common.UserId;

@Repository
public interface ReportRepository extends BaseRepository<ReportEntity> {

    boolean existsReportEntityByWorkoutPlanId(Long workoutPlanId);

    boolean existsReportEntityByUserIdAndInitialIsTrue(UserId userId);
}
