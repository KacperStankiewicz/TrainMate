package pl.edu.pja.trainmate.core.common.exception;

import org.springframework.http.HttpStatus;
import pl.edu.pja.trainmate.core.common.error.BaseErrorCode;

public class CommonException extends BaseException {

    public CommonException(String code, HttpStatus status) {
        super(code, status);
    }

    public CommonException() {
    }

    public CommonException(String code) {
        super(code);
    }

    public CommonException(String code, String description, HttpStatus status) {
        super(code, description, status);
    }

    public CommonException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
