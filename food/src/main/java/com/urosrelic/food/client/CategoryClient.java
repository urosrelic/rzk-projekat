package com.urosrelic.food.client;

import com.urosrelic.food.beans.GetCategoriesByIdsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "category-service")
public interface CategoryClient {
    @PostMapping("/getByIds")
    ResponseEntity<?> getCategoriesByIds(@RequestBody GetCategoriesByIdsRequest request);

    @GetMapping("/exists/{id}")
    boolean existsById(@PathVariable String id);
}
