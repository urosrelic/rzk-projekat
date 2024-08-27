package com.urosrelic.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantCreationRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String address;
    private String phone;
    private String description;
    private double rating;
    private List<String> foods;
    private String openinTime;
    private String closingTime;
}
