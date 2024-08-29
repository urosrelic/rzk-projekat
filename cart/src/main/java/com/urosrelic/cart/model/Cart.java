package com.urosrelic.cart.model;

import com.urosrelic.cart.dto.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    private String id;
    private String user;
    private List<CartItem> cartItems;
    private double cartTotal;
    private boolean confirmed;

    public Cart(String user, List<CartItem> cartItems, double cartTotal, boolean confirmed) {
        this.user = user;
        this.cartItems = cartItems;
        this.cartTotal = cartTotal;
        this.confirmed = confirmed;
    }
}