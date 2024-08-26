package com.urosrelic.auth.controller;

import com.urosrelic.auth.dto.TokenRequest;
import com.urosrelic.auth.dto.UserLoginRequest;
import com.urosrelic.auth.dto.UserRegisterRequest;
import com.urosrelic.auth.handler.ResponseType;
import com.urosrelic.auth.enums.Role;
import com.urosrelic.auth.exception.WrongCredentialsException;
import com.urosrelic.auth.handler.ResponseHandler;
import com.urosrelic.auth.model.User;
import com.urosrelic.auth.security.JwtService;
import com.urosrelic.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        Optional<User> userOptional = authService.getUserByEmail(userRegisterRequest.getEmail());
        if (userOptional.isPresent()) {
            return ResponseHandler.generateResponse(ResponseType.ERROR, "Email is already taken", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setName(userRegisterRequest.getName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setRole(Optional.ofNullable(userRegisterRequest.getRole()).orElse(Role.USER));
        user.setPassword(userRegisterRequest.getPassword());

        authService.registerUser(user);

        return ResponseHandler.generateResponse(ResponseType.SUCCESS, "User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    private ResponseEntity<Object> login(@RequestBody UserLoginRequest loginRequest) {
        try {
            String token = authService.login(loginRequest);
            return ResponseHandler.generateResponseWithBody(ResponseType.SUCCESS, "Login successful", HttpStatus.OK, token);
        } catch (WrongCredentialsException e) {
            return ResponseHandler.generateResponse(ResponseType.ERROR, "Wrong credentials", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Object> validateToken(@RequestBody TokenRequest tokenRequest) {
        try {
            String token = tokenRequest.getToken().trim();
            log.info("Validating token: " + token);
            if (token.contains(" ")) {
                throw new io.jsonwebtoken.MalformedJwtException("Token contains whitespace");
            }
            String email = jwtService.extractUsername(token);
            UserDetails userDetails = authService.loadUserDetails(email);
            if (authService.isTokenValid(token, userDetails)) {
                return ResponseHandler.generateResponse(ResponseType.SUCCESS, "Token is valid", HttpStatus.OK);
            } else {
                return ResponseHandler.generateResponse(ResponseType.ERROR, "Token is invalid", HttpStatus.UNAUTHORIZED);
            }
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            return ResponseHandler.generateResponse(ResponseType.ERROR, "Malformed JWT token", HttpStatus.BAD_REQUEST);
        }
    }

}
