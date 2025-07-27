package com.ecommerce.project.Controller;

import com.ecommerce.project.Config.AppConstant;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long categoryId) {

        ProductDTO savedProductDTO = productService.addProduct(productDTO , categoryId);
        return new ResponseEntity<>(savedProductDTO , HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam (name = "pageNumber" , defaultValue = AppConstant.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam (name = "pageSize" , defaultValue = AppConstant.PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam (name = "sortBy" , defaultValue = AppConstant.SORT_PRODUCT_BY , required = false) String sortBy,
            @RequestParam (name = "sortOrder" , defaultValue = AppConstant.SORT_ORDER , required = false) String sortOrder
            ){
        ProductResponse productResponse = productService.getAllProduct(pageNumber , pageSize , sortBy , sortOrder);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/product")
    public ResponseEntity<ProductResponse> getProductByCategory(@RequestParam (name = "pageNumber" , defaultValue = AppConstant.PAGE_NUMBER , required = false) Integer pageNumber,
                                                                @RequestParam (name = "pageSize" , defaultValue = AppConstant.PAGE_SIZE , required = false) Integer pageSize,
                                                                @RequestParam (name = "sortBy" , defaultValue = AppConstant.SORT_PRODUCT_BY , required = false) String sortBy,
                                                                @RequestParam (name = "sortOrder" , defaultValue = AppConstant.SORT_ORDER , required = false) String sortOrder,
                                                                @PathVariable Long categoryId){
        ProductResponse productResponse = productService.searchByCategory(categoryId, pageNumber , pageSize , sortBy , sortOrder);
        return new ResponseEntity<>(productResponse , HttpStatus.CREATED);

    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword(@RequestParam (name = "pageNumber" , defaultValue = AppConstant.PAGE_NUMBER , required = false) Integer pageNumber,
                                                               @RequestParam (name = "pageSize" , defaultValue = AppConstant.PAGE_SIZE , required = false) Integer pageSize,
                                                               @RequestParam (name = "sortBy" , defaultValue = AppConstant.SORT_PRODUCT_BY , required = false) String sortBy,
                                                               @RequestParam (name = "sortOrder" , defaultValue = AppConstant.SORT_ORDER , required = false) String sortOrder,
                                                               @PathVariable String keyword){
        ProductResponse productResponse = productService.searchProductByKeyword(keyword,  pageNumber , pageSize , sortBy , sortOrder);

        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long productId){
        ProductDTO updatedProductDTO = productService.updateProduct(productDTO , productId);
        return new ResponseEntity<>(updatedProductDTO , HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        ProductDTO productDTO = productService.deleteProduct(productId);

        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PutMapping("product/{productId}/image")
    public ResponseEntity<ProductDTO> uploadImage(@RequestParam("file") MultipartFile file , @PathVariable Long productId) throws IOException {
        ProductDTO updatedProduct = productService.uploadImage(productId , file);

        return new ResponseEntity<>(updatedProduct , HttpStatus.OK);
    }
}
