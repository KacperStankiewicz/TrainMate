package pl.edu.pja.trainmate.core.domain.exercise;

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

    private Integer repetitions;
    private String tempo;
    private Double weight;
    private Integer rir;
    private Integer sets;
}
