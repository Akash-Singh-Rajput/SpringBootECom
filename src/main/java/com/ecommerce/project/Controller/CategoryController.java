package com.ecommerce.project.Controller;


import com.ecommerce.project.Model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @GetMapping("api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(){
        CategoryResponse response = categoryService.getAllCategories();
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @PostMapping("api/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);

        return new ResponseEntity<>(savedCategoryDTO , HttpStatus.CREATED);
    }

    @DeleteMapping("api/public/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){


        String status = categoryService.deleteCategory(id);
        return new ResponseEntity<>(status , HttpStatus.OK);

    }

    @PutMapping("api/public/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id, @Valid @RequestBody Category category){


        Category updatedCategory = categoryService.updateCategory(id , category);

        return new ResponseEntity<>("successfully update category with id: " + id , HttpStatus.OK);

    }

}
