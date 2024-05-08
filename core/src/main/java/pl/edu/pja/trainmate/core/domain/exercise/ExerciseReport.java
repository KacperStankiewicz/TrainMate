package pl.edu.pja.trainmate.core.domain.exercise;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class ExerciseReport {

    private Integer reportedRepetitions;
    private Integer reportedWeight;
    private Integer reportedRir;
    private Integer reportedSets;
    private String remarks;
    @Builder.Default
    private boolean reviewed = false;

    public void markAsReviewed() {
        this.reviewed = true;
    }
}
