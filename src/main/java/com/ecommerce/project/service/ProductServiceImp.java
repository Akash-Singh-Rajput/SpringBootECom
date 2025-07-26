package com.ecommerce.project.service;

import com.ecommerce.project.Exception.ResourceNotFoundException;
import com.ecommerce.project.Model.Category;
import com.ecommerce.project.Model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO , Long categoryID) {
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category" , "CategoryId" , categoryID));

        Product product =  modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        product.setImage("default.png");
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01 ) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        productRepository.save(product);
        return  modelMapper.map(product ,  ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProduct() {
        List<Product> products = productRepository.findAll();

        List<ProductDTO> productDTOList = products.stream()
                .map(product -> modelMapper.map(product , ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOList);

        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category" , "CategoryId" , categoryId));


        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);

        List<ProductDTO> productDTOList = products.stream()
                .map(product -> modelMapper.map(product , ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOList);

        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');

        List<ProductDTO> productDTOList = products.stream()
                .map(product -> modelMapper.map(product , ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOList);

        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product" , "productId" , productId));

        Product product = modelMapper.map(productDTO, Product.class);
        
        existingProduct.setPrice(product.getPrice());
        existingProduct.setSpecialPrice(product.getSpecialPrice());
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDiscount(product.getDiscount());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setDescription(product.getDescription());

        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01 ) * product.getPrice());
        existingProduct.setSpecialPrice(specialPrice);

        Product updatedProduct = productRepository.save(existingProduct);
        return  modelMapper.map(updatedProduct ,  ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow( () -> new  ResourceNotFoundException("Product" , "productId" , productId));

        productRepository.delete(product);

        return modelMapper.map(product ,  ProductDTO.class);
    }

    @Override
    public ProductDTO uploadImage(Long productId, MultipartFile file) throws IOException {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() ->  new ResourceNotFoundException("Product" , "productId" , productId));

        String fileName = fileService.uploadImagePath(path , file);

        productFromDb.setImage(fileName);
        Product updatedProduct = productRepository.save(productFromDb);

        return modelMapper.map(updatedProduct ,  ProductDTO.class);

    }


}
