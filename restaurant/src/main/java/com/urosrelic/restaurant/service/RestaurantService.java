package com.urosrelic.restaurant.service;

import com.urosrelic.restaurant.dto.RestaurantCreationRequest;
import com.urosrelic.restaurant.model.Restaurant;
import com.urosrelic.restaurant.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public Restaurant addRestaurant(RestaurantCreationRequest request) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        return restaurantRepository.save(restaurant);
    }

    public boolean existsById(String id) {
        return restaurantRepository.existsById(id);
    }

    public Restaurant getRestaurant(String id) {
        return restaurantRepository.findById(id).orElse(null);
    }
}
