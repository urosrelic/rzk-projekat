package com.urosrelic.cart.beans;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Restaurant {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String description;
    private Double rating;
    private List<String> foods;
    private String openinTime;
    private String closingTime;
}
