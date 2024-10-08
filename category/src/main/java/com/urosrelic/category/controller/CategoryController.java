package com.urosrelic.category.controller;

import com.urosrelic.category.dto.CategoryCreationRequest;
import com.urosrelic.category.dto.GetCategoriesByIdsRequest;
import com.urosrelic.category.handler.ResponseHandler;
import com.urosrelic.category.handler.ResponseType;
import com.urosrelic.category.model.Category;
import com.urosrelic.category.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/auth/add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryCreationRequest categoryRequest) {
        Category category = categoryService.addCategory(categoryRequest);

        if (category == null) {
            return ResponseHandler.generateResponse(ResponseType.ERROR, "Error creating category", HttpStatus.BAD_REQUEST);
        }

        return ResponseHandler.generateResponseWithBody(ResponseType.SUCCESS, "Category created successfully", HttpStatus.CREATED, category);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        if (categories.isEmpty()) {
            return ResponseHandler.generateResponseWithBody(ResponseType.SUCCESS, "Empty categories", HttpStatus.ACCEPTED, categories);
        }

        return ResponseHandler.generateResponseWithBody(ResponseType.SUCCESS, "Categories retrieved successfully", HttpStatus.OK, categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryData(@PathVariable String id) {
        Category category = categoryService.getCategoryData(id);

        if (category == null) {
            return ResponseHandler.generateResponse(ResponseType.ERROR, "No category found with that ID", HttpStatus.NOT_FOUND);
        }

        return ResponseHandler.generateResponseWithBody(ResponseType.SUCCESS, "Category retrieved successfully", HttpStatus.OK, category);
    }

    @PostMapping("/getByIds")
    public ResponseEntity<?> getCategoriesByIds(@RequestBody GetCategoriesByIdsRequest request) {
        List<Category> categories = categoryService.getCategoriesByIds(request.getIds());

        if (categories.isEmpty()) {
            return ResponseHandler.generateResponseWithBody(ResponseType.SUCCESS, "Empty categories", HttpStatus.ACCEPTED, categories);
        }

        return ResponseHandler.generateResponseWithBody(ResponseType.SUCCESS, "Categories retrieved successfully", HttpStatus.OK, categories);
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable String id) {
        return categoryService.existsById(id);
    }
}
