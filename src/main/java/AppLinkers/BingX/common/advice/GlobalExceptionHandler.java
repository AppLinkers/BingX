package AppLinkers.BingX.common.advice;

import AppLinkers.BingX.common.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IOException.class})
    protected ResponseEntity<ErrorResponse> handleIOException(IOException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
