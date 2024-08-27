package com.urosrelic.restaurant.repository;

import com.urosrelic.restaurant.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
}
