package com.urosrelic.restaurant.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@Getter
@Setter
public class Restaurant {
    @Id
    private String id;
    private String name;
    private String address;
    private String phone;
    private String description;
    private double rating;
    private List<String> foods;
    private String openinTime;
    private String closingTime;
}
