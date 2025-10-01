package com.github.kprusina.exception;

import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private I18nMessageResolver i18nMessageResolver;

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex,
          HttpHeaders headers,
          HttpStatusCode status,
          WebRequest request) {

    Map<String, Set<String>> errors = ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.groupingBy(
                    FieldError::getField,
                    Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())
            ));

    Map<String, Set<String>> globalErrors = ex.getBindingResult().getGlobalErrors().stream()
            .collect(Collectors.groupingBy(
                    ObjectError::getObjectName,
                    Collectors.mapping(ObjectError::getDefaultMessage, Collectors.toSet())
            ));

    errors.putAll(globalErrors);
    log.error("Validation failed: {}", errors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(SoftException.class)
  protected ResponseEntity<Object> handleSoftException(SoftException ex) {
    I18nMessage message = i18nMessageResolver.translate(ex.getMessageKey(), ex.getArgs());
    log.error(message.getMessage(), ex);
    return new ResponseEntity<>(message, ex.getStatus());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
    Map<String, Set<String>> errors = ex.getConstraintViolations().stream()
            .collect(Collectors.groupingBy(
                    cv -> cv.getPropertyPath().toString(),
                    Collectors.mapping(cv -> cv.getMessage(), Collectors.toSet())
            ));
    log.error("Constraint violations: {}", errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleGeneric(Exception ex) {
    log.error("Unexpected exception", ex);
    Map<String, Object> body = Map.of(
            "status", 500,
            "message", "Unexpected error occurred. Please try again later."
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}
