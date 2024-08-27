package com.urosrelic.food.dto;

import com.urosrelic.food.enums.FoodUnit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FoodCreationRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Unit is mandatory")
    private FoodUnit unit;

    @NotNull(message = "Price is mandatory")
    private Double price;

    @NotBlank(message = "Restaurant is mandatory")
    private String restaurant;

    private List<String> categories;
}
