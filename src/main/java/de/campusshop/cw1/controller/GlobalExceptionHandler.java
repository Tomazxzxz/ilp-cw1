package de.campusshop.cw1.controller;

import de.campusshop.cw1.dto.ErrorResponse;
import de.campusshop.cw1.exception.DuplicateOrderException;
import de.campusshop.cw1.exception.OrderNotFoundException;
import de.campusshop.cw1.exception.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Maps exceptions to stable {@link ErrorResponse} bodies and the correct HTTP status codes
 * (Week 4: {@code @ControllerAdvice} / {@code @ExceptionHandler}).
 *
 * <p>TODO (student): add {@code @ExceptionHandler} methods that map:
 * <ul>
 *   <li>{@link MethodArgumentNotValidException} and {@link HttpMessageNotReadableException} → {@code 400};</li>
 *   <li>{@link OrderNotFoundException} → {@code 404};</li>
 *   <li>{@link DuplicateOrderException} → {@code 409};</li>
 *   <li>{@link StorageException} / any other exception → {@code 500}.</li>
 * </ul>
 * Each handler should return a stable {@link ErrorResponse} (code, message, timestamp).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("ORDER_NOT_FOUND", ex.getMessage()));
    }
    @ExceptionHandler(DuplicateOrderException.class)
    public ResponseEntity<ErrorResponse> handleDuplicates(DuplicateOrderException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("DUPLICATE_ORDER", ex.getMessage()));
    }

    @ExceptionHandler ({
        MethodArgumentNotValidException.class,HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("BAD_REQUEST", ex.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("INTERNAL_ERROR", ex.getMessage()));
    }
}
