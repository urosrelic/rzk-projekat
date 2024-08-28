package com.urosrelic.cart.handler;

import com.urosrelic.cart.exception.CartItemAlreadyExistsException;
import com.urosrelic.cart.exception.FoodNotFoundException;
import com.urosrelic.cart.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return ResponseHandler.generateResponse(ResponseType.ERROR, "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
