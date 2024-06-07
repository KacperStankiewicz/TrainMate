package pl.edu.pja.trainmate.core.common;

import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;

@Getter
@NoArgsConstructor(access = PRIVATE, force = true)
@Embeddable
@EqualsAndHashCode
public class DateRange {

    @NotNull
    private LocalDate from;
    @NotNull
    private LocalDate to;

    @PersistenceConstructor
    @QueryProjection
    public DateRange(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }
}
