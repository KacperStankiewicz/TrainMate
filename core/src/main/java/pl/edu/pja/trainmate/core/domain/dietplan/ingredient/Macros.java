package pl.edu.pja.trainmate.core.domain.dietplan.ingredient;

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
public class Macros {

    private Integer fat;
    private Integer carbs;
    private Integer protein;
    private Integer calories;
}
