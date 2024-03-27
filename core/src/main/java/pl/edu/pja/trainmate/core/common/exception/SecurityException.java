package pl.edu.pja.trainmate.core.common.exception;

import pl.edu.pja.trainmate.core.common.error.BaseErrorCode;

public class SecurityException extends BaseException {

    public SecurityException(BaseErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getHttpStatus());
    }
}