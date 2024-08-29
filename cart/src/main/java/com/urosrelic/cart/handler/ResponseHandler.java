package com.urosrelic.cart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(ResponseType responseType, String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        if (responseType == ResponseType.SUCCESS) {
            map.put("message", message);
        } else {
            map.put("error", message);
        }

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponseWithBody(ResponseType responseType, String message, HttpStatus status, Object body) {
        Map<String, Object> map = new HashMap<>();
        if (responseType == ResponseType.SUCCESS) {
            map.put("message", message);
            map.put("body", body);
        } else {
            map.put("error", message);
        }

        return new ResponseEntity<>(map, status);
    }
}
