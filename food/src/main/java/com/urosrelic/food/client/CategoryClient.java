package com.urosrelic.food.client;

import com.urosrelic.food.beans.GetCategoriesByIdsRequest;
import jakarta.ws.rs.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "category-service")
public interface CategoryClient {
    @GetMapping("/api/v1/category/getByIds")
    ResponseEntity<?> getCategoriesByIds(@RequestBody GetCategoriesByIdsRequest request);

    @GetMapping("/api/v1/category/existsById/{id}")
    boolean existsById(@PathVariable("id") String id);
}
