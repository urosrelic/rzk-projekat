package com.urosrelic.category.service;

import com.urosrelic.category.dto.CategoryCreationRequest;
import com.urosrelic.category.model.Category;
import com.urosrelic.category.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category getCategoryData(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category addCategory(CategoryCreationRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        return categoryRepository.save(category);
    }

    public List<Category> getCategoriesByIds(List<String> ids) {
        return categoryRepository.findAllById(ids);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public boolean existsById(String id) {
        return categoryRepository.existsById(id);
    }
}

