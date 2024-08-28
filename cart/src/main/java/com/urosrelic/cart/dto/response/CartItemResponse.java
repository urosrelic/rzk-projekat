package com.urosrelic.cart.dto.response;

import com.urosrelic.cart.beans.Food;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Food food;
    private int quantity;
    private double price;
}
