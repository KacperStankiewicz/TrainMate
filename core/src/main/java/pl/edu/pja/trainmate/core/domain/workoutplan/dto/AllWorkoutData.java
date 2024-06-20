package pl.edu.pja.trainmate.core.domain.workoutplan.dto;

import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.domain.training.querydsl.TrainingUnitProjection;

@Value
@NoArgsConstructor(access = PRIVATE, force = true)
public class AllWorkoutData {

    Long id;
    Long version;
    String name;
    String category;
    List<TrainingUnitProjection> trainingUnits = new ArrayList<>();

    @QueryProjection
    public AllWorkoutData(Long id, Long version, String name, String category) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.category = category;
    }

    public void addTrainingUnits(List<TrainingUnitProjection> trainingUnits) {
        if (trainingUnits != null) {
            this.trainingUnits.addAll(trainingUnits);
        }
    }
}