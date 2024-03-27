package pl.edu.pja.trainmate.core.common.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.edu.pja.trainmate.core.common.error.ErrorMessageDto;

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    ResponseEntity<List<ErrorMessageDto>> handle(BaseException ex) {

        log.warn(ex.getMessage());

        var code = ex.getCode();
        var description = ex.getMessage();
        var status = ex.getStatus();

        var errorMessages = List.of(ErrorMessageDto.withDescription(code, description));

        return new ResponseEntity<>(errorMessages, status);
    }

    @ExceptionHandler(Exception.class)
    List<ErrorMessageDto> handleUnmappedException(Exception ex) {

        log.error(ex.getMessage());
        log.debug(ex.getMessage(), ex);

        return List.of(ErrorMessageDto.withDescription("INTERNAL_SERVER_ERROR", ex.getMessage()));
    }
}