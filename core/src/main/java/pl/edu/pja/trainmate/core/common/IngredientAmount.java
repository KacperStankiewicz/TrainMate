package pl.edu.pja.trainmate.core.common;

import static lombok.AccessLevel.PRIVATE;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
@Getter
public class IngredientAmount {

    private Long amount;
    private UnitOfMeasurement unitOfMeasurement;
}
