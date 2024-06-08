package pl.edu.pja.trainmate.core.domain.exercise;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edu.pja.trainmate.core.converter.SetParamsConverter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class ExerciseReport {

    @Builder.Default
    @Column(name = "reported_sets", columnDefinition = "clob")
    @Convert(converter = SetParamsConverter.class)
    List<SetParams> reportedSets = new ArrayList<>();
    private String remarks;
    @Builder.Default
    private boolean reviewed = false;

    public void markAsReviewed() {
        this.reviewed = true;
    }
}
