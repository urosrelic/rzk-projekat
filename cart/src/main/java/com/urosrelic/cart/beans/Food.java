package com.urosrelic.cart.beans;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class Food {
    private String id;
    private String name;
    private double price;
    private String restaurant;
    private List<String> categories;
}