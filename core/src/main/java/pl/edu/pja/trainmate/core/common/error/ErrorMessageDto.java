package pl.edu.pja.trainmate.core.common.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class ErrorMessageDto {

    private String code;
    private String description;

    public ErrorMessageDto(String code) {
        this(code, null);
    }

    public ErrorMessageDto(BaseErrorCode error) {
        this.code = error.getCode();
        this.description = error.getMessage();
    }

    public static ErrorMessageDto withDescription(String code, String description) {
        return ErrorMessageDto.builder()
            .code(code)
            .description(description)
            .build();
    }
}