package com.urosrelic.user.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service", url = "${application.config.users-url}")
public interface UserClient {
}
