package com.ecommerce.project.repository;

import com.ecommerce.project.Model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
}
