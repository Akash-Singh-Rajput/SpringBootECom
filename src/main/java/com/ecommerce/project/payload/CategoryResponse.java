package com.ecommerce.project.payload;

import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryResponse {
    private List<CategoryDTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElement;
    private Integer totalPages;
    private Boolean lastPage;
}
