package com.urosrelic.gateway.filter;

import com.urosrelic.gateway.beans.TokenRequest;
import com.urosrelic.gateway.client.AuthClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private final RouteValidator routeValidator;
    private final AuthClient authClient;

    public AuthenticationFilter(RouteValidator routeValidator, AuthClient authClient) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.authClient = authClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    log.error("Missing authorization header");
                    return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authorization header"));
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                String token = null;

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                    log.info("Token: {}", token);
                }

                if (token == null || token.isEmpty()) {
                    log.error("Invalid authorization header format");
                    return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authorization header"));
                }

                TokenRequest tokenRequest = new TokenRequest();
                tokenRequest.setToken(token);

                return authClient.validateToken(tokenRequest)
                        .doOnSuccess(aVoid -> {
                            // Token validation passed
                        })
                        .doOnError(e -> {
                            log.error("Token validation failed: {}", e.getMessage());
                            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access to application");
                        })
                        .then(chain.filter(exchange));
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Configuration properties, if any
    }
}
