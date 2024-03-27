package pl.edu.pja.trainmate.core.common;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
@Getter
public class IngredientAmount {

    private Integer amount;

    @Enumerated(STRING)
    private UnitOfMeasurement unitOfMeasurement;
}
