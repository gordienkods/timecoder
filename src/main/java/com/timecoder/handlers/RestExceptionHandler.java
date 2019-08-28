package com.timecoder.handlers;

import com.timecoder.EpisodeExistsException;
import com.timecoder.PostExistsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EpisodeExistsException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EpisodeExistsException ex) {
        ApiError apiError = new ApiError(CONFLICT, ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(PostExistsException.class)
    protected ResponseEntity<Object> handlePostNotFound(PostExistsException ex){
        ApiError apiError = new ApiError(NOT_FOUND, ex.getMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
