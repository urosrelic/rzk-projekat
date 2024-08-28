package com.urosrelic.cart.service;

import com.urosrelic.cart.beans.Category;
import com.urosrelic.cart.beans.Food;
import com.urosrelic.cart.beans.Restaurant;
import com.urosrelic.cart.beans.User;
import com.urosrelic.cart.client.AuthClient;
import com.urosrelic.cart.client.CategoryClient;
import com.urosrelic.cart.client.FoodClient;
import com.urosrelic.cart.client.RestaurantClient;
import com.urosrelic.cart.dto.CartItem;
import com.urosrelic.cart.dto.response.CartItemResponse;
import com.urosrelic.cart.dto.response.CartResponse;
import com.urosrelic.cart.dto.response.FoodResponse;
import com.urosrelic.cart.dto.response.RestaurantResponse;
import com.urosrelic.cart.exception.*;
import com.urosrelic.cart.model.Cart;
import com.urosrelic.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final AuthClient authClient;
    private final FoodClient foodClient;
    private final CategoryClient categoryClient;
    private final RestaurantClient resturantClient;

    public Cart removeFromCart(String foodId, String token) {
        User user = authClient.getUser(token);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        Cart cart = cartRepository.findByUserAndConfirmedFalse(user.getId());
        if (cart == null) {
            throw new CartEmptyException("Cart is empty");
        }

        log.info(cart.getCartItems().toString());

        for (CartItem item : cart.getCartItems()) {
            log.info("Cart item: {}", item.getFood());
            log.info("Food id: {}", foodId);

            if (item.getFood().equals(foodId)) {
                log.info("Removing item from cart");
                cart.getCartItems().remove(item);
            } else {
                throw new CartItemNotFoundException("Item not found in cart");
            }
        }

        if (cart.getCartItems().isEmpty()) {
            cartRepository.delete(cart);
        }

        cartRepository.save(cart);

        double totalPrice = 0;
        for (CartItem item : cart.getCartItems()) {
            totalPrice += item.getPrice() * item.getQuantity();
        }

        cart.setCartTotal(totalPrice);
        cartRepository.save(cart);

        return cartRepository.findById(cart.getId())
                .orElseThrow(() -> new GenericException("Error removing item from cart"));
    }

    public Cart editQuantity(String foodId, int quantity, String token) {
        User user = authClient.getUser(token);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        Cart cart = cartRepository.findByUserAndConfirmedFalse(user.getId());
        if (cart == null) {
            throw new CartEmptyException("Cart is empty");
        }

        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(item -> item.getFood().equals(foodId))
                .findFirst();

        if (cartItemOptional.isEmpty()) {
            throw new CartItemNotFoundException("Item not found in cart");
        }

        CartItem cartItem = cartItemOptional.get();
        cartItem.setQuantity(quantity);

        cartRepository.save(cart);

        double totalPrice = 0;
        for (CartItem item : cart.getCartItems()) {
            totalPrice += item.getPrice() * item.getQuantity();
        }

        cart.setCartTotal(totalPrice);
        cartRepository.save(cart);

        return cartRepository.findById(cart.getId())
                .orElseThrow(() -> new GenericException("Error editing item quantity"));
    }

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

    public CartResponse getCartInformation(String token) {
        User user = authClient.getUser(token);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        Cart cart = cartRepository.findByUserAndConfirmedFalse(user.getId());
        if (cart == null) {
            throw new CartEmptyException("Cart is empty");
        }

        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());

        List<CartItemResponse> cartItems = new ArrayList<>();

        for (CartItem item : cart.getCartItems()) {
            Optional<Food> foodOptional = foodClient.getFoodById(item.getFood());
            if (foodOptional.isEmpty()) {
                throw new FoodNotFoundException("Food not found");
            }

            FoodResponse foodResponse = getFoodById(item.getFood());

            CartItemResponse cartItemResponse = new CartItemResponse();
            cartItemResponse.setFood(foodResponse);
            cartItemResponse.setQuantity(item.getQuantity());
            cartItemResponse.setPrice(item.getPrice());
            cartItems.add(cartItemResponse);
        }

        cartResponse.setCartItems(cartItems);
        cartResponse.setCartTotal(cart.getCartTotal());
        cartResponse.setConfirmed(cart.isConfirmed());
        return cartResponse;
    }

    public FoodResponse getFoodById(String foodId) {
        Optional<Food> foodOptional = foodClient.getFoodById(foodId);
        if (foodOptional.isEmpty()) {
            throw new FoodNotFoundException("Food not found");
        }

        log.info("Food found: {}", foodOptional.get());

        Food food = foodOptional.get();
        FoodResponse foodResponse = new FoodResponse();
        foodResponse.setId(food.getId());
        foodResponse.setName(food.getName());
        foodResponse.setPrice(food.getPrice());

        RestaurantResponse restaurantResponse = getRestaurantById(food.getRestaurant());
        foodResponse.setRestaurant(restaurantResponse);

        List<Category> categories = new ArrayList<>();

        for (String categoryId : food.getCategories()) {
            Category category = categoryClient.getCategoryData(categoryId);
            if (category == null) {
                throw new CategoryNotFoundException("Category not found");
            }
            categories.add(category);
        }

        foodResponse.setCategories(categories);

        return foodResponse;
    }

    public RestaurantResponse getRestaurantById(String restaurantId) {

        Restaurant restaurant = resturantClient.getRestaurant(restaurantId);

        if (restaurant == null) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }

        RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.setName(restaurant.getName());

        return restaurantResponse;
    }

}
