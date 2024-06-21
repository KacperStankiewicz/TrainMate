package pl.edu.pja.trainmate.core.domain.report.dto;

import static lombok.AccessLevel.PRIVATE;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
public class PeriodicalReportUpdateDto extends PeriodicalReportDto {

    Long reportId;
    Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PeriodicalReportUpdateDto that = (PeriodicalReportUpdateDto) o;
        return Objects.equals(reportId, that.reportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId);
    }
}
