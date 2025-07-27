package com.ecommerce.project.Controller;


import com.ecommerce.project.Config.AppConstant;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @GetMapping("api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber" , defaultValue = AppConstant.PAGE_NUMBER , required = false) Integer pageNumber ,
             @RequestParam(name = "pageSize" , defaultValue = AppConstant.PAGE_SIZE , required = false) Integer pageSize ,
            @RequestParam(name = "sortOrder" , defaultValue = AppConstant.SORT_ORDER , required = false) String sortOrder ,
            @RequestParam(name = "sortBy" , defaultValue = AppConstant.SORT_CATEGORY_BY, required = false) String sortBy
            ) {

        CategoryResponse response = categoryService.getAllCategories(pageNumber , pageSize , sortOrder , sortBy);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @PostMapping("api/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);

        return new ResponseEntity<>(savedCategoryDTO , HttpStatus.CREATED);
    }

    @DeleteMapping("api/public/categories/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id){

        CategoryDTO deleteCategory = categoryService.deleteCategory(id);
        return new ResponseEntity<>(deleteCategory , HttpStatus.OK);

    }

    @PutMapping("api/public/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO){


        CategoryDTO updatedCategory = categoryService.updateCategory(id , categoryDTO);

        return new ResponseEntity<>(updatedCategory , HttpStatus.OK);

    }

}
