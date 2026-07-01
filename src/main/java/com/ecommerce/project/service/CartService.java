package com.ecommerce.project.service;
import java.util.*;

import com.ecommerce.project.Model.Cart;
import com.ecommerce.project.Model.Product;
import com.ecommerce.project.payload.CartDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCart();

    CartDTO getUserCart(String email, Long cartId);

    String deleteProductFromCart(Long cartId, Long prodId);

    @Transactional
    CartDTO updateProductQuantity(Long prodId, Integer quantity);

    void updateProductInCarts(Long cartId, Long productId);
}
