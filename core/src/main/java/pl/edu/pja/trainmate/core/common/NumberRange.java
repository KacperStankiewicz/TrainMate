package pl.edu.pja.trainmate.core.common;

import static lombok.AccessLevel.PRIVATE;

import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PRIVATE, force = true)
@EqualsAndHashCode
public class NumberRange {

    @NotNull
    private Long from;
    private Long to;


    public NumberRange(Long from, Long to) {
        this.from = from;
        this.to = to;
    }
}
