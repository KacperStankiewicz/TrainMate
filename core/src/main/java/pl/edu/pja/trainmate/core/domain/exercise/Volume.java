package pl.edu.pja.trainmate.core.domain.exercise;

import javax.persistence.Column;
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

    private Integer weight;

    @Column(name = "target_rir")
    private Integer targetRir;

    @Column(name = "actual_rir")
    private Integer actualRir;

    private Integer sets;
}
