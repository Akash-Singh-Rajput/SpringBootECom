package com.ecommerce.project.service;

import com.ecommerce.project.Model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

import java.util.ArrayList;
import java.util.List;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber , Integer PageSize);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long id);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
}
