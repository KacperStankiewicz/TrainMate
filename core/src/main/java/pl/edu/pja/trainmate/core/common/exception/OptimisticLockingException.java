package pl.edu.pja.trainmate.core.common.exception;

import static org.springframework.http.HttpStatus.CONFLICT;
import static pl.edu.pja.trainmate.core.common.errorcode.CommonErrorCode.RESOURCE_HAS_BEEN_MODIFIED_BY_ANOTHER_USER;

public class OptimisticLockingException extends BaseException {

    public OptimisticLockingException(String resource) {
        super(RESOURCE_HAS_BEEN_MODIFIED_BY_ANOTHER_USER, resource + " has been modified by another user.", CONFLICT);
    }
}
