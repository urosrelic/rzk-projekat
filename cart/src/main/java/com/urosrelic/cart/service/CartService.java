package com.urosrelic.cart.service;

import com.urosrelic.cart.beans.Food;
import com.urosrelic.cart.beans.User;
import com.urosrelic.cart.client.AuthClient;
import com.urosrelic.cart.client.FoodClient;
import com.urosrelic.cart.handler.ResponseHandler;
import com.urosrelic.cart.handler.ResponseType;
import com.urosrelic.cart.model.Cart;
import com.urosrelic.cart.dto.CartItem;
import com.urosrelic.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final AuthClient authClient;
    private final FoodClient foodClient;

    public ResponseEntity<Object> addToCart(String foodId, int quantity, String token) {
        User user = authClient.getUser(token);

        Optional<Food> foodOptional = foodClient.getFoodById(foodId);
        if (foodOptional.isEmpty()) {
            return ResponseHandler.generateResponse(ResponseType.ERROR, "Food not found", HttpStatus.NOT_FOUND);
        }

        Food food = foodOptional.get();
        Cart cart = cartRepository.findByUserAndConfirmedFalse(user.getId());

        double totalPrice = 0;

        if (cart == null) {
            CartItem createdCartItem = new CartItem(foodId, quantity, food.getPrice());
            totalPrice = createdCartItem.getPrice() * createdCartItem.getQuantity();

            cart = new Cart(user.getId(), List.of(createdCartItem), totalPrice, false);
            cartRepository.save(cart);
        } else {
            Optional<CartItem> existingCartItemOptional = cart.getCartItems().stream()
                    .filter(item -> item.getFood().equals(foodId))
                    .findFirst();

            if (existingCartItemOptional.isPresent()) {
                return ResponseHandler.generateResponse(ResponseType.ERROR, "Item already in cart", HttpStatus.BAD_REQUEST);
            } else {
                CartItem createdCartItem = new CartItem(foodId, quantity, food.getPrice());
                cart.getCartItems().add(createdCartItem);
                cartRepository.save(cart);

                totalPrice += food.getPrice() * quantity;
            }

            totalPrice = 0;
            for (CartItem item : cart.getCartItems()) {
                totalPrice += item.getPrice() * item.getQuantity();
            }

            cart.setCartTotal(totalPrice);
            cartRepository.save(cart);
        }

        Optional<Cart> populatedCart = cartRepository.findById(cart.getId());

        if (populatedCart.isEmpty()) {
            return ResponseHandler.generateResponse(ResponseType.ERROR, "Error populating cart", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(populatedCart, HttpStatus.OK);
    }
}