package com.urosrelic.cart.client;

import com.urosrelic.cart.beans.Food;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "food-service")
public interface FoodClient {
    @GetMapping("/api/v1/food/{id}")
    Optional<Food> getFoodById(@PathVariable("id") String id);
}
