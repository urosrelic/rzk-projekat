package com.urosrelic.cart.client;

import com.urosrelic.cart.beans.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service")
public interface AuthClient {
    @GetMapping("/api/v1/auth/get-user-data")
    ResponseEntity<Object> getUserData(@RequestHeader("Authorization") String token);

    @GetMapping("/api/v1/auth/get-user")
    User getUser(@RequestHeader("Authorization") String token);
}
