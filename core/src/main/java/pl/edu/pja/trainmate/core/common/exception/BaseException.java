package pl.edu.pja.trainmate.core.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import pl.edu.pja.trainmate.core.common.errorcode.BaseErrorCode;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class BaseException extends RuntimeException {

    private final String code;
    private final HttpStatus status;

    public BaseException(String code) {
        this.code = code;
        this.status = null;
    }

    public BaseException(String code, String description, HttpStatus status) {
        super(description);
        this.code = code;
        this.status = status;
    }

    public BaseException(BaseErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage(), errorCode.getHttpStatus());
    }

    public BaseException(BaseErrorCode errorCode, Object... args) {
        this(errorCode.getCode(), errorCode.format(args), errorCode.getHttpStatus());
    }
}