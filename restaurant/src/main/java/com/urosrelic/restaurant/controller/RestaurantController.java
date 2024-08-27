package com.urosrelic.restaurant.controller;

import com.urosrelic.restaurant.dto.RestaurantCreationRequest;
import com.urosrelic.restaurant.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurant")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public void addRestaurant(@RequestBody RestaurantCreationRequest request) {
        restaurantService.addRestaurant(request);
    }

    @GetMapping("/{id}")
    public boolean existsById(@PathVariable String id) {
        return restaurantService.existsById(id);
    }
}
