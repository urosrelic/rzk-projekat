package com.urosrelic.food.dto;

import com.urosrelic.food.enums.FoodUnit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FoodCreationRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Unit is mandatory")
    private FoodUnit unit;
    @NotBlank(message = "Price is mandatory")
    private double price;
    @NotBlank(message = "Restaurant is mandatory")
    private String restaurant;
    @NotEmpty(message = "Categories are mandatory")
    private String[] categories;
}
