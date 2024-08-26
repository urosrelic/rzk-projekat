package com.urosrelic.food.model;

import com.urosrelic.food.enums.FoodUnit;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
public class Food {
    @Id
    private String id;
    private String name;
    private FoodUnit unit;
    private double price;
    private String restaurant;
    private List<String> categories;
}
