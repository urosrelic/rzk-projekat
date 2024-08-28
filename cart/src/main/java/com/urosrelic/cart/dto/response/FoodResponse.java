package com.urosrelic.cart.dto.response;

import com.urosrelic.cart.beans.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FoodResponse {
    private String id;
    private String name;
    private double price;
    private RestaurantResponse restaurant;
    private List<Category> categories;
}
