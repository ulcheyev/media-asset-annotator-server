package cz.cvut.fel.annotator.shared.exception.handler;

import cz.cvut.fel.annotator.shared.exception.MediaCmsException;
import cz.cvut.fel.annotator.shared.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        log.warn("Resource not found: {}", ex.getMessage());

        return buildResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MediaCmsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadGateway(
            MediaCmsException ex,
            HttpServletRequest request
    ) {
        log.error("Upstream service failure", ex);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error("Unhandled exception", ex);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected server error. " + ex.getMessage(),
                request.getRequestURI()
        );
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(
            HttpStatus status,
            String message,
            String path
    ) {
        return ResponseEntity
                .status(status)
                .body(new ApiErrorResponse(
                        Instant.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        message,
                        path
                ));
    }
}
