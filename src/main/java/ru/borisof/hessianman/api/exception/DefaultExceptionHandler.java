package ru.borisof.hessianman.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public RestErrorModel handleValidationException(ValidationException e) {
        return RestErrorModel.of(e.getMessage(), e.getCause().getMessage());
    }

    @ExceptionHandler(InvocationException.class)
    public RestErrorModel handleInvocationException(InvocationException e) {
        return RestErrorModel.of(e.getMessage(), e.getMessage());
    }

}
