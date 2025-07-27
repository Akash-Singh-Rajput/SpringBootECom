package com.ecommerce.project.payload;

import com.ecommerce.project.Model.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {


    private Long productId;
    private String productName;
    private String image;

    @NotBlank
    @Size(min = 5, message = "description should contains atleast 5 character")
    private String description;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;


}
