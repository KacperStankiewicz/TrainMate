package pl.edu.pja.trainmate.core.common.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

public class OptimisticLockingException extends BaseException {

    public OptimisticLockingException(String resource) {
        super("RESOURCE_HAS_BEEN_MODIFIED_BY_ANOTHER_USER", resource + " has been modified by another user.", CONFLICT);
    }
}