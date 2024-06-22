package pl.edu.pja.trainmate.core.domain.report.dto;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.domain.exercise.SetParams;

@Value
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = PRIVATE, force = true)
public class ReportCreateDto {

    Long exerciseItemId;
    List<SetParams> sets;
    String remarks;
    Long version;
}
