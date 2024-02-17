package pl.edu.pja.trainmate.core.domain.workoutplan.exercise;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Volume {

    private Long repetitions;

    private String tempo;

    private Long weight;

    private Long rir;

    private Long sets;
}
