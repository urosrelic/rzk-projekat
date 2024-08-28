package com.urosrelic.cart.controller;

import com.urosrelic.cart.dto.AddToCartRequest;
import com.urosrelic.cart.handler.ResponseHandler;
import com.urosrelic.cart.handler.ResponseType;
import com.urosrelic.cart.model.Cart;
import com.urosrelic.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Object> addToCart(@RequestBody AddToCartRequest request, @RequestHeader("Authorization") String token) {
        Cart cart = cartService.addToCart(request.getFoodId(), request.getQuantity(), token);
        return ResponseHandler.generateResponseWithBody(ResponseType.SUCCESS, "Item added to cart", HttpStatus.OK, cart);
    }
}
