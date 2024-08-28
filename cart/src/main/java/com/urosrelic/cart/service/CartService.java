package com.urosrelic.cart.service;

import com.urosrelic.cart.beans.Food;
import com.urosrelic.cart.beans.User;
import com.urosrelic.cart.client.AuthClient;
import com.urosrelic.cart.client.FoodClient;
import com.urosrelic.cart.dto.CartItem;
import com.urosrelic.cart.exception.CartItemAlreadyExistsException;
import com.urosrelic.cart.exception.FoodNotFoundException;
import com.urosrelic.cart.exception.GenericException;
import com.urosrelic.cart.exception.UserNotFoundException;
import com.urosrelic.cart.model.Cart;
import com.urosrelic.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public Cart addToCart(String foodId, int quantity, String token) {
        User user;
        try {
            user = authClient.getUser(token);
        } catch (Exception ex) {
            throw new UserNotFoundException("User not found");
        }

        Optional<Food> foodOptional = foodClient.getFoodById(foodId);
        if (foodOptional.isEmpty()) {
            throw new FoodNotFoundException("Food not found");
        }

        Food food = foodOptional.get();
        Cart cart = cartRepository.findByUserAndConfirmedFalse(user.getId());

        if (cart == null) {
            CartItem createdCartItem = new CartItem(foodId, quantity, food.getPrice());
            double totalPrice = createdCartItem.getPrice() * createdCartItem.getQuantity();

            cart = new Cart(user.getId(), List.of(createdCartItem), totalPrice, false);
            cartRepository.save(cart);
        } else {
            Optional<CartItem> existingCartItemOptional = cart.getCartItems().stream()
                    .filter(item -> item.getFood().equals(foodId))
                    .findFirst();

            if (existingCartItemOptional.isPresent()) {
                throw new CartItemAlreadyExistsException("Item already in cart");
            } else {
                CartItem createdCartItem = new CartItem(foodId, quantity, food.getPrice());
                cart.getCartItems().add(createdCartItem);
                cartRepository.save(cart);

                double totalPrice = 0;
                for (CartItem item : cart.getCartItems()) {
                    totalPrice += item.getPrice() * item.getQuantity();
                }

                cart.setCartTotal(totalPrice);
                cartRepository.save(cart);
            }
        }

        return cartRepository.findById(cart.getId())
                .orElseThrow(() -> new GenericException("Error adding item to cart"));
    }
}
