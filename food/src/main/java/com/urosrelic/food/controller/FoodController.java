package com.urosrelic.food.controller;

import com.urosrelic.food.dto.FoodCreationRequest;
import com.urosrelic.food.handler.ResponseHandler;
import com.urosrelic.food.handler.ResponseType;
import com.urosrelic.food.model.Food;
import com.urosrelic.food.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/food")
@AllArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @PostMapping("/auth/add")
    public ResponseEntity<?> addFood(FoodCreationRequest request) {
        return foodService.saveFood(request);
    }

    @GetMapping
    public ResponseEntity<?> getAllFoods() {
        List<Food> foods = foodService.getAllFoods();

        if (foods.isEmpty()) {
            return ResponseHandler.generateResponse(ResponseType.SUCCESS, "Foods are empty", HttpStatus.ACCEPTED);
        }

        return ResponseHandler.generateResponseWithBody(ResponseType.SUCCESS, "Foods retrieved successfully", HttpStatus.OK, foods);
    }
}
