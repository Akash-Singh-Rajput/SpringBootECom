package com.ecommerce.project.service;

import com.ecommerce.project.Exception.APIException;
import com.ecommerce.project.Exception.ResourceNotFoundException;
import com.ecommerce.project.Model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImp implements CategoryService{


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(){
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new APIException("No category has been added yet");
        }

        List<CategoryDTO> categoryDTOList = categories.stream()
                .map(category -> modelMapper.map(category , CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOList);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category = modelMapper.map(categoryDTO , Category.class);

        Category categoryDb = categoryRepository.findByCategoryName(category.getCategoryName());

        if(categoryDb != null) {
            throw new APIException("Category with category name: " + category.getCategoryName() + " already exist !!");
        }
        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory , CategoryDTO.class);
    }

    @Override
    public String deleteCategory(Long id) {

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId" , id));

        categoryRepository.delete(existingCategory);

        return "Category with id : " + id +  "is successfully deleted";


    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId" , id));

        existingCategory.setCategoryName(category.getCategoryName());
        return categoryRepository.save(existingCategory);
    }
}
