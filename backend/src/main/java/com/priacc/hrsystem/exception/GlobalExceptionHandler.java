package com.priacc.hrsystem.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle ResourceNotFoundException
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                      WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "RESOURCE_NOT_FOUND"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle BadCredentialsException
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handleBadCredentialsException(BadCredentialsException exception,
                                                                    WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "Invalid username or password",
                webRequest.getDescription(false),
                "INVALID_CREDENTIALS"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle AccessDeniedException
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception,
                                                                  WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "You don't have permission to access this resource",
                webRequest.getDescription(false),
                "ACCESS_DENIED"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                            WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle validation exceptions
     */
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        ValidationErrorDetails errorDetails = new ValidationErrorDetails(
                LocalDateTime.now(),
                "Validation Failed",
                request.getDescription(false),
                "VALIDATION_ERROR",
                errors
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Error details class for standard errors
     */
    public static class ErrorDetails {
        private LocalDateTime timestamp;
        private String message;
        private String details;
        private String errorCode;

        public ErrorDetails(LocalDateTime timestamp, String message, String details, String errorCode) {
            this.timestamp = timestamp;
            this.message = message;
            this.details = details;
            this.errorCode = errorCode;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public String getMessage() {
            return message;
        }

        public String getDetails() {
            return details;
        }

        public String getErrorCode() {
            return errorCode;
        }
    }

    /**
     * Error details class for validation errors
     */
    public static class ValidationErrorDetails extends ErrorDetails {
        private Map<String, String> errors;

        public ValidationErrorDetails(LocalDateTime timestamp, String message, String details, String errorCode,
                                     Map<String, String> errors) {
            super(timestamp, message, details, errorCode);
            this.errors = errors;
        }

        public Map<String, String> getErrors() {
            return errors;
        }
    }
}