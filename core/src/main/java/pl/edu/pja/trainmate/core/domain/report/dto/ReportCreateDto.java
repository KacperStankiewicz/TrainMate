package pl.edu.pja.trainmate.core.domain.report.dto;

import lombok.Value;

@Value
public class ReportCreateDto {

    Long exerciseItemId;
    Integer repetitions;
    Integer weight;
    Integer rir;
    Integer sets;
    String remarks;
}
