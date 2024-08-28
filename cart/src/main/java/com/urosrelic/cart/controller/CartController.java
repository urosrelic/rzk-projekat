package com.urosrelic.cart.controller;

import com.urosrelic.cart.dto.request.AddToCartRequest;
import com.urosrelic.cart.dto.request.RemoveCartItemRequest;
import com.urosrelic.cart.dto.response.CartResponse;
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

    @GetMapping
    public ResponseEntity<Object> getCart(@RequestHeader("Authorization") String token) {
        CartResponse cart = cartService.getCartInformation(token);
        return ResponseHandler.generateResponseWithBody(ResponseType.SUCCESS, "Cart retrieved", HttpStatus.OK, cart);
    }

    @PatchMapping
    public ResponseEntity<Object> editQuantity(@RequestBody AddToCartRequest request, @RequestHeader("Authorization") String token) {
        Cart cart = cartService.editQuantity(request.getFoodId(), request.getQuantity(), token);
        return ResponseHandler.generateResponseWithBody(ResponseType.SUCCESS, "Item quantity updated", HttpStatus.OK, cart);
    }

    @DeleteMapping("/remove-from-cart")
    public ResponseEntity<Object> removeFromCart(@RequestBody RemoveCartItemRequest removeCartItemRequest, @RequestHeader("Authorization") String token) {
        Cart cart = cartService.removeFromCart(removeCartItemRequest.getFoodId(), token);
        return ResponseHandler.generateResponseWithBody(ResponseType.SUCCESS, "Item removed from cart", HttpStatus.OK, cart);
    }
}
