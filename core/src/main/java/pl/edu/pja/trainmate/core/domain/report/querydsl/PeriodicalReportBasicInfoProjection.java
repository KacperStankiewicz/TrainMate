package pl.edu.pja.trainmate.core.domain.report.querydsl;

import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(access = PRIVATE, force = true)
public class PeriodicalReportBasicInfoProjection {

    Long id;
    Boolean reviewed;

    @QueryProjection
    public PeriodicalReportBasicInfoProjection(Long id, Boolean reviewed) {
        this.id = id;
        this.reviewed = reviewed;
    }
}
