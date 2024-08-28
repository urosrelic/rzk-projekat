package com.urosrelic.cart.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartResponse {
    private String id;
    private List<CartItemResponse> cartItems;
    private double cartTotal;
    private boolean confirmed;
}
