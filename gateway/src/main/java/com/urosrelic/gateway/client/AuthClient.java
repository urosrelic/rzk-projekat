package com.urosrelic.gateway.client;

import com.urosrelic.gateway.beans.TokenRequest;
import com.urosrelic.gateway.beans.TokenResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
// WebFLux is not compatible with OpenFeign
public class AuthClient {
    private final WebClient webClient;

    public AuthClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://auth-service:9898").build();
    }

    public Mono<TokenResponse> validateToken(TokenRequest tokenRequest) {
        return webClient.post()
                .uri("/api/v1/auth/validate")
                .bodyValue(tokenRequest)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .doOnNext(response -> log.info("Received TokenResponse: {}", response));
    }

    public Mono<TokenResponse> extractRole(TokenRequest tokenRequest) {
        return webClient.post()
                .uri("/api/v1/auth/role")
                .bodyValue(tokenRequest)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .doOnNext(response -> log.info("Role response: {}", response));
    }
}

