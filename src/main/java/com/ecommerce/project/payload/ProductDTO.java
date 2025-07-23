package com.ecommerce.project.payload;

import com.ecommerce.project.Model.Category;
import jakarta.persistence.*;

public class ProductDTO {


    private Long productId;
    private String productName;
    private String image;
    private Integer description;
    private Integer quantity;
    private double price;
    private double specialPrice;


}
