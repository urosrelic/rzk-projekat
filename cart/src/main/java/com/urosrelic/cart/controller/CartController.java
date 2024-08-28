package com.urosrelic.cart.controller;

import com.urosrelic.cart.dto.AddToCartRequest;
import com.urosrelic.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Object> addToCart(@RequestBody AddToCartRequest request, @RequestHeader("Authorization") String token) {
        String foodId = request.getFoodId();
        int quantity = request.getQuantity();
        return cartService.addToCart(foodId, quantity, token);
    }
}
