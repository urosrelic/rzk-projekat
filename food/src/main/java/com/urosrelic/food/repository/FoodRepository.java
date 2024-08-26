package com.urosrelic.food.repository;

import com.urosrelic.food.model.Food;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodRepository extends MongoRepository<Food, String> {
}
