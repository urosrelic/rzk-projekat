package com.urosrelic.restaurant.controller;

import com.urosrelic.restaurant.dto.RestaurantCreationRequest;
import com.urosrelic.restaurant.handler.ResponseHandler;
import com.urosrelic.restaurant.handler.ResponseType;
import com.urosrelic.restaurant.model.Restaurant;
import com.urosrelic.restaurant.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurant")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<?> addRestaurant(@RequestBody RestaurantCreationRequest request) {
        Restaurant restaurant = restaurantService.addRestaurant(request);

        if (restaurant == null) {
            return ResponseHandler.generateResponse(ResponseType.ERROR, "Error adding restaurant", HttpStatus.BAD_REQUEST);
        }

        return ResponseHandler.generateResponseWithBody(ResponseType.SUCCESS, "Restaurant created", HttpStatus.CREATED, restaurant);
    }

    @GetMapping("/existsById/{id}")
    public boolean existsById(@PathVariable String id) {
        return restaurantService.existsById(id);
    }
}
