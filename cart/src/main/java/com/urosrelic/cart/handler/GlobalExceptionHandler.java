package com.urosrelic.cart.handler;

import com.urosrelic.cart.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FoodNotFoundException.class)
    public ResponseEntity<Object> handleFoodNotFoundException(FoodNotFoundException ex) {
        return ResponseHandler.generateResponse(ResponseType.ERROR, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseHandler.generateResponse(ResponseType.ERROR, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartItemAlreadyExistsException.class)
    public ResponseEntity<Object> handleCartItemAlreadyExistsException(CartItemAlreadyExistsException ex) {
        return ResponseHandler.generateResponse(ResponseType.ERROR, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartEmptyException.class)
    public ResponseEntity<Object> handleCartEmptyException(CartEmptyException ex) {
        return ResponseHandler.generateResponse(ResponseType.ERROR, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<Object> handleRestaurantNotFoundException(RestaurantNotFoundException ex) {
        return ResponseHandler.generateResponse(ResponseType.ERROR, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Object> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return ResponseHandler.generateResponse(ResponseType.ERROR, ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        log.error("Unhandled exception: ", ex);
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "An unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }


}
