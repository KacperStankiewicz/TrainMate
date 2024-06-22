package pl.edu.pja.trainmate.core.domain.report.dto;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.domain.file.dto.FileStorageDto;

@Value
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
public class PeriodicalReportCreateDto extends PeriodicalReportDto {

    Long workoutPlanId;

    @Builder
    public PeriodicalReportCreateDto(Double weight, Integer bodyFat, Double leftBiceps, Double rightBiceps, Double leftForearm, Double rightForearm,
        Double leftThigh, Double rightThigh, Double leftCalf, Double rightCalf, Double shoulders, Double chest, Double waist, Double abdomen, Double hips,
        List<FileStorageDto> images, Long workoutPlanId) {
        super(weight, bodyFat, leftBiceps, rightBiceps, leftForearm, rightForearm, leftThigh, rightThigh, leftCalf, rightCalf, shoulders, chest, waist, abdomen,
            hips, images);
        this.workoutPlanId = workoutPlanId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PeriodicalReportCreateDto dto = (PeriodicalReportCreateDto) o;
        return Objects.equals(workoutPlanId, dto.workoutPlanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workoutPlanId);
    }
}
