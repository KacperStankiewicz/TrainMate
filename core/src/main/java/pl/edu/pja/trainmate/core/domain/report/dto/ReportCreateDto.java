package pl.edu.pja.trainmate.core.domain.report.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = PRIVATE, force = true)
public class ReportCreateDto {

    Long exerciseItemId;
    Integer repetitions;
    Integer weight;
    Integer rir;
    Integer sets;
    String remarks;
}
