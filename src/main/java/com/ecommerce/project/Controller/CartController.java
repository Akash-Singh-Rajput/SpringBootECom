package com.ecommerce.project.Controller;

import com.ecommerce.project.Exception.APIException;
import com.ecommerce.project.Exception.ResourceNotFoundException;
import com.ecommerce.project.Model.Cart;
import com.ecommerce.project.Model.CartItem;
import com.ecommerce.project.Model.Product;
import com.ecommerce.project.Util.AuthUtil;
import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.repository.CartRepository;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("api/")
public class CartController {

    @Autowired
    AuthUtil authUtil;

    @Autowired
    CartService cartService;

    @Autowired
    CartRepository cartRepository;

    @PostMapping("/cart/addProduct/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId , @PathVariable Integer quantity){
        CartDTO cartDTO = cartService.addProductToCart(productId , quantity);
        return new ResponseEntity<>(cartDTO , HttpStatus.OK);
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getAllCart(){
        List<CartDTO> cartDTOS = cartService.getAllCart();
        return new ResponseEntity<>(cartDTOS , HttpStatus.FOUND);
    }

    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getCartById(){
        String email = authUtil.getCurrentUserEmail();

        Cart cart = cartRepository.findCartByEmail(email);
        if(cart == null){
            throw new APIException("Oops!! No cart exists");
        }
        Long cartId = cart.getCartId();

        CartDTO cartDTO = cartService.getUserCart(email , cartId);

        return new ResponseEntity<>(cartDTO , HttpStatus.FOUND);

    }

    @PutMapping("/cart/product/{prodId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateProductQuantity(@PathVariable Long prodId, @PathVariable String operation){

        int quantity = operation.equalsIgnoreCase("add") ? 1 : -1;

        CartDTO cartDTO = cartService.updateProductQuantity(prodId, quantity);

        return new ResponseEntity<>(cartDTO , HttpStatus.ACCEPTED);
    }

    @DeleteMapping("cart/{cartId}/product/{prodId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long cartId, @PathVariable Long prodId){
        String productDeleted = cartService.deleteProductFromCart(cartId, prodId);
        return new ResponseEntity<>(productDeleted, HttpStatus.OK);
    }
}
