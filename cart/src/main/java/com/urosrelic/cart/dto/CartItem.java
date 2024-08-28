package com.urosrelic.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class CartItem {
    private String food;
    private int quantity;
    private double price;
}
