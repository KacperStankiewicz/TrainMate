package pl.edu.pja.trainmate.core.common.exception;

import pl.edu.pja.trainmate.core.common.error.BaseErrorCode;

public class EmailException extends BaseException {

    public EmailException(BaseErrorCode errorCode, Object... args) {
        super(errorCode.getCode(), errorCode.format(args), errorCode.getHttpStatus());
    }
}
