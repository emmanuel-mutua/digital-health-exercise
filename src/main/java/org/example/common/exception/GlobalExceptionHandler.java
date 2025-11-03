package org.example.common.exception;

import org.example.common.response.DhGenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<DhGenericResponse<?>> handleApiException(ApiException ex, WebRequest request) {
        DhGenericResponse<?> response = DhGenericResponse.builder()
                .success(false)
                .data(null)
                .message(ex.getMessage())
                .debugMessage(ex.getMessage())
                .build();
        response.setData(null);
        return ResponseEntity.status(ex.getStatus()).body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<DhGenericResponse<?>> handleAll(Exception ex, WebRequest request) {

        DhGenericResponse<?> response = DhGenericResponse.builder()
                .success(false)
                .data(null)
                .message(ex.getMessage())
                .debugMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
