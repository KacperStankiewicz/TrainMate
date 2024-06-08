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
public class SetParams {

    private Integer reportedRepetitions;
    private Integer reportedWeight;
    private Integer reportedRir;
    private Integer set;
}
