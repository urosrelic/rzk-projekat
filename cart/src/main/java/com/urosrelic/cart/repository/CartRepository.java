package com.urosrelic.cart.repository;

import com.urosrelic.cart.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {
    Cart findByUser(String user);

    Cart findByUserAndConfirmedFalse(String id);
}
