package com.urosrelic.auth.service;

import com.urosrelic.auth.dto.UserLoginRequest;
import com.urosrelic.auth.exception.WrongCredentialsException;
import com.urosrelic.auth.model.User;
import com.urosrelic.auth.repository.UserRepository;
import com.urosrelic.auth.security.JwtService;
import com.urosrelic.auth.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    public boolean isTokenValid(String token, UserDetails user) {
        return jwtService.isValid(token, user);
    }

    public UserDetails loadUserDetails(String email) {
        return userDetailsService.loadUserByUsername(email);
    }

    public String generateToken(User user) {
        return jwtService.generateToken(user);
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public String login(UserLoginRequest loginRequest) throws WrongCredentialsException {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new WrongCredentialsException("User doesn't exist"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new WrongCredentialsException("Password incorrect");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        return generateToken(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
