package com.ecommerce.project.Controller;


import com.ecommerce.project.Model.Category;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @GetMapping("api/public/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> list = categoryService.getAllCategories();
        return new ResponseEntity<>(list , HttpStatus.OK);
    }

    @PostMapping("api/public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
        categoryService.createCategory(category);

        return new ResponseEntity<>("Category successfully created" , HttpStatus.CREATED);
    }

    @DeleteMapping("api/public/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){

        try{
            String status = categoryService.deleteCategory(id);
            return new ResponseEntity<>(status , HttpStatus.OK);
        } catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason() , e.getStatusCode());
        }
    }

    @PutMapping("api/public/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id, @RequestBody Category category){

        try{
            Category updatedCategory = categoryService.updateCategory(id , category);
            return new ResponseEntity<>("successfully update category with id: " + id , HttpStatus.OK);
        } catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason() , e.getStatusCode());
        }
    }

}
