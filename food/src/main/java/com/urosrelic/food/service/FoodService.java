package com.urosrelic.food.service;

import com.urosrelic.food.client.CategoryClient;
import com.urosrelic.food.dto.FoodCreationRequest;
import com.urosrelic.food.enums.FoodUnit;
import com.urosrelic.food.handler.ResponseHandler;
import com.urosrelic.food.handler.ResponseType;
import com.urosrelic.food.model.Food;
import com.urosrelic.food.repository.FoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final CategoryClient categoryClient;
    // TODO add restaurant client -> check if restaurant exists

    public ResponseEntity<Object> saveFood(FoodCreationRequest request) {
        List<String> categories = List.of(request.getCategories());

        for (String category : categories) {
            if (!categoryClient.existsById(category)) {
                return ResponseHandler.generateResponse(ResponseType.ERROR, "Category does not exist", HttpStatus.NOT_FOUND);
            }
        }

        Food food = new Food();
        food.setName(request.getName());
        food.setCategories(categories);
        food.setPrice(request.getPrice());

        if (!isUnitValid(String.valueOf(request.getUnit()))) {
            return ResponseHandler.generateResponse(ResponseType.ERROR, "Invalid unit: only MEAL and GRAM are accepted", HttpStatus.BAD_REQUEST);
        }

        food.setUnit(request.getUnit());
        food.setRestaurant(request.getRestaurant());

        foodRepository.save(food);

        return ResponseHandler.generateResponseWithBody(ResponseType.SUCCESS, "Food created successfully", HttpStatus.CREATED, food);
    }

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public boolean isUnitValid(String unit) {
        for (FoodUnit foodUnit : FoodUnit.values()) {
            if (foodUnit.name().equals(unit)) {
                return true;
            }
        }
        return false;
    }
}
