package com.urosrelic.user.controller;

import com.urosrelic.user.dto.UserRegisterRequest;
import com.urosrelic.user.enums.ResponseType;
import com.urosrelic.user.enums.Role;
import com.urosrelic.user.model.User;
import com.urosrelic.user.service.UserService;
import com.urosrelic.user.handler.ResponseHandler;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        Optional<User> userOptional = userService.getUserByEmail(userRegisterRequest.getEmail());
        if (userOptional.isPresent()) {
            return ResponseHandler.generateResponse(ResponseType.ERROR, "Email is already taken", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setName(userRegisterRequest.getName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setRole(Optional.ofNullable(userRegisterRequest.getRole()).orElse(Role.USER));
        user.setPassword(userRegisterRequest.getPassword());

        userService.registerUser(user);

        return ResponseHandler.generateResponse(ResponseType.SUCCESS, "User registered successfully", HttpStatus.CREATED);
    }
}
