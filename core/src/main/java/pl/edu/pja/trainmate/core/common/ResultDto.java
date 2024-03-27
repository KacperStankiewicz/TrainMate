package pl.edu.pja.trainmate.core.common;

import static pl.edu.pja.trainmate.core.common.ResultStatus.ERROR;
import static pl.edu.pja.trainmate.core.common.ResultStatus.SUCCESS;

import java.util.Optional;
import lombok.Value;
import pl.edu.pja.trainmate.core.common.error.BaseErrorCode;


@Value
public class ResultDto<T> {

    T value;
    BaseErrorCode error;
    ResultStatus status;

    private ResultDto() {
        this.value = null;
        this.error = null;
        this.status = ERROR;
    }

    private ResultDto(T value) {
        this.status = SUCCESS;
        this.error = null;
        this.value = value;
    }

    private ResultDto(BaseErrorCode error) {
        this.status = ERROR;
        this.value = null;
        this.error = error;
    }

    public static <T> ResultDto<T> ofValue(T value) {
        return Optional.ofNullable(value)
            .map(ResultDto::new)
            .orElseGet(ResultDto::new);
    }

    public static <T> ResultDto<T> ofError(BaseErrorCode code) {
        return new ResultDto<>(code);
    }

    public static <T> ResultDto<T> ofValueOrError(T value, BaseErrorCode code) {
        return Optional.ofNullable(value)
            .map(ResultDto::new)
            .orElseGet(() -> new ResultDto<>(code));
    }
}
