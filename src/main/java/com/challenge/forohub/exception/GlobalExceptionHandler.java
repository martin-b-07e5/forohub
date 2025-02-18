package com.challenge.forohub.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @Autowired
  private HttpServletRequest request;


  @ExceptionHandler(NameAlreadyInUseException.class)
  public ResponseEntity<Map<String, Object>> handleNameAlreadyInUse(NameAlreadyInUseException ex) {
    // Crear el mapa de respuesta
    Map<String, Object> response = new HashMap<>();
    response.put("timestamp", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    response.put("status", HttpStatus.FORBIDDEN.value());
    response.put("error", "Forbidden");
    response.put("message", ex.getMessage());

    // Retornar la respuesta con el código de estado 403 y el mapa como cuerpo
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }


  // Handler for PostNotFoundException (custom validation exception)
  @ExceptionHandler(PostNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handlePostNotFoundException(PostNotFoundException ex) {
    Map<String, Object> response = Map.of(
        "timestamp", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
        "status", HttpStatus.NOT_FOUND.value(),
        "error", "Not Found",
        "message", ex.getMessage(),
        "path", request.getRequestURI()
    );
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // Retorna 404 Not Found
  }

/*  @ExceptionHandler(PatientNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handlePatientNotFoundException(PatientNotFoundException ex) {
    Map<String, Object> response = Map.of(
        "message", ex.getMessage(),
        "timestamp", LocalDateTime.now(),
        "error", "Not Found",
        "path", request.getRequestURI(),
        "status", HttpStatus.NOT_FOUND.value()
    );

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // Retorna 404 Not Found
  }*/

/*  @ExceptionHandler(NoAvailableDoctorException.class)
  public ResponseEntity<Map<String, Object>> handleNoAvailableDoctorException(NoAvailableDoctorException ex) {
    Map<String, Object> response = Map.of(
        "timestamp", LocalDateTime.now(),
        "status", HttpStatus.NOT_FOUND.value(),
        "error", "Not Found",
        "message", ex.getMessage(),
        "path", request.getRequestURI()
    );

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }*/


  // Handler for IllegalStateException
  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalStateException(IllegalStateException ex) {
    Map<String, Object> response = Map.of(
        "a-timestamp", LocalDateTime.now(),
        "b-status", HttpStatus.CONFLICT.value(),
        "c-error", "Conflict",
        "d-message", ex.getMessage(),
        "e-path", request.getRequestURI()
    );

    return new ResponseEntity<>(response, HttpStatus.CONFLICT); // Retorna 409 Conflict
  }

  // Handler for ValidationException (custom validation exception)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
    // Crear una respuesta personalizada
    Map<String, Object> response = new HashMap<>();
    response.put("timestamp1", LocalDateTime.now());
    response.put("timestamp2", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
    response.put("status", HttpStatus.BAD_REQUEST.value());
    response.put("error", "Bad Request");
    response.put("message", "Validation failed");

    // Obtener los errores de validación
    List<String> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
        .collect(Collectors.toList());

    // Incluir los detalles de los errores en la respuesta
    response.put("errors", errors);

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  // Handler for IllegalArgumentException
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("timestamp", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    response.put("status", HttpStatus.BAD_REQUEST.value());
    response.put("error", "Bad Request");
    response.put("message", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

}
