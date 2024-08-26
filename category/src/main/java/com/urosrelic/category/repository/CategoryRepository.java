package com.urosrelic.category.repository;

import com.urosrelic.category.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
