package com.urosrelic.cart.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {
    private String foodId;
    private int quantity;
}
