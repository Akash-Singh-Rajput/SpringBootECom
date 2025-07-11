package com.ecommerce.project.service;

import com.ecommerce.project.Exception.APIException;
import com.ecommerce.project.Exception.ResourceNotFoundException;
import com.ecommerce.project.Model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImp implements CategoryService{


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber , Integer pageSize){

        Pageable pageDetails = PageRequest.of(pageNumber , pageSize);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);


        List<Category> categories = categoryPage.getContent();
        if(categories.isEmpty()){
            throw new APIException("No category has been added yet");
        }

        List<CategoryDTO> categoryDTOList = categories.stream()
                .map(category -> modelMapper.map(category , CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOList);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElement(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());

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
    public CategoryDTO deleteCategory(Long id) {

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId" , id));

        categoryRepository.delete(existingCategory);

        return modelMapper.map(existingCategory , CategoryDTO.class);


    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO , Category.class);
        Category existingCategory = categoryRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId" , id));

        existingCategory.setCategoryName(category.getCategoryName());
        Category savedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(savedCategory , CategoryDTO.class);
    }
}
