package pl.edu.pja.trainmate.core.domain.exercise.dto;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.edu.pja.trainmate.core.domain.exercise.SetParams;

@Value
@NoArgsConstructor(force = true, access = PRIVATE)
@AllArgsConstructor
@Builder
public class ExerciseReportDto {

    Long version;
    List<SetParams> reportedSets;
    String remarks;
}
