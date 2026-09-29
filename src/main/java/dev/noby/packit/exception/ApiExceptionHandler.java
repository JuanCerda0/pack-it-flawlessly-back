package dev.noby.packit.exception;

import dev.noby.packit.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(PackageNotFoundException.class)
    ResponseEntity<ApiError> handlePackageNotFound(PackageNotFoundException exception, HttpServletRequest request) {
        return error(HttpStatus.NOT_FOUND, "PACKAGE_NOT_FOUND", exception.getMessage(), request);
    }

    @ExceptionHandler(PackageItemNotFoundException.class)
    ResponseEntity<ApiError> handleItemNotFound(PackageItemNotFoundException exception, HttpServletRequest request) {
        return error(HttpStatus.NOT_FOUND, "PACKAGE_ITEM_NOT_FOUND", exception.getMessage(), request);
    }

    @ExceptionHandler(InvalidPackageStatusTransitionException.class)
    ResponseEntity<ApiError> handleInvalidStatus(InvalidPackageStatusTransitionException exception,
                                                  HttpServletRequest request) {
        return error(HttpStatus.CONFLICT, "INVALID_STATUS_TRANSITION", exception.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));
        return error(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", message, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ApiError> handleUnreadableBody(HttpMessageNotReadableException exception,
                                                   HttpServletRequest request) {
        return error(HttpStatus.BAD_REQUEST, "INVALID_REQUEST_BODY",
                "The request body is missing or contains an invalid value", request);
    }

    private String formatFieldError(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }

    private ResponseEntity<ApiError> error(HttpStatus status, String code, String message,
                                           HttpServletRequest request) {
        return ResponseEntity.status(status).body(
                new ApiError(Instant.now(), status.value(), code, message, request.getRequestURI()));
    }
}
