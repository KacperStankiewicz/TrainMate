package pl.edu.pja.trainmate.core.domain.report.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edu.pja.trainmate.core.domain.file.dto.FileStorageDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PeriodicalReportDto {

    private Double weight;
    private Integer bodyFat;
    private Double leftBiceps;
    private Double rightBiceps;
    private Double leftForearm;
    private Double rightForearm;
    private Double leftThigh;
    private Double rightThigh;
    private Double leftCalf;
    private Double rightCalf;

    private Double shoulders;
    private Double chest;
    private Double waist;
    private Double abdomen;
    private Double hips;
    private List<FileStorageDto> images;
}
