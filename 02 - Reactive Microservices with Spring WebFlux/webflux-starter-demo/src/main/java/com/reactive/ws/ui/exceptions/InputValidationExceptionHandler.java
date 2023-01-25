package com.reactive.ws.ui.exceptions;


import com.reactive.ws.ui.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationExceptionHandler {
    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<ErrorResponse> handleException(InputValidationException ex){
        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(ex.getErrorCode());
        response.setInput(ex.getInput());
        response.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
