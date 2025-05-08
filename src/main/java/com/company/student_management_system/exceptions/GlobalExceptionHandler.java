package com.company.student_management_system.exceptions;

import com.company.student_management_system.dto.ErrorDto;
import com.company.student_management_system.dto.HttpApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<HttpApiResponse<ErrorDto>> error_404(ItemNotFoundException e, HttpServletRequest request) {
        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .errorBody(e.getMessage())
                .errorPath(request.getRequestURI())
                .build();

        HttpApiResponse<ErrorDto> response = HttpApiResponse.<ErrorDto>builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .success(false)
                .data(errorDto)
                .message("Item not found")
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpApiResponse<ErrorDto>> notValidExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, List<String>> errorBody = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errorBody.compute(field, (s, strings) -> {
                strings = Objects.requireNonNullElse(strings, new ArrayList<>());
                strings.add(message);
                return strings;
            });
        }
        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(HttpStatus.NOT_ACCEPTABLE.value())
                .errorBody(errorBody)
                .errorPath(request.getRequestURI())
                .build();

        HttpApiResponse<ErrorDto> response = HttpApiResponse.<ErrorDto>builder()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                .success(false)
                .data(errorDto)
                .message("Validation failed")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }
}
