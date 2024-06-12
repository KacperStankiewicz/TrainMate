package pl.edu.pja.trainmate.core.domain.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Value
public class EmailParamsDto {

    String to;
    String subject;
    String content;
}
