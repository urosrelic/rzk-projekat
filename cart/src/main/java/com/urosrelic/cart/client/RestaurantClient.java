package com.urosrelic.cart.client;

import com.urosrelic.cart.beans.Restaurant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-service")
public interface RestaurantClient {
    @GetMapping("/api/v1/restaurant/{id}")
    Restaurant getRestaurant(@PathVariable String id);
}
