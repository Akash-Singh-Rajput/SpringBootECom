package com.ecommerce.project.service;

import com.ecommerce.project.Model.Category;

import java.util.ArrayList;
import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    void createCategory(Category category);

    String deleteCategory(Long id);

    Category updateCategory(Long id, Category category);
}
