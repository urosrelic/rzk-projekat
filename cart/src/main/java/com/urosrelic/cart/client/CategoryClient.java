package com.urosrelic.cart.client;

import com.urosrelic.cart.beans.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service")
public interface CategoryClient {
    @GetMapping("/api/v1/category/{id}")
    Category getCategoryData(@PathVariable("id") String id);
}
