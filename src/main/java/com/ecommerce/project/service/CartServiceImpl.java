package com.ecommerce.project.service;

import com.ecommerce.project.Exception.APIException;
import com.ecommerce.project.Exception.ResourceNotFoundException;
import com.ecommerce.project.Model.Cart;
import com.ecommerce.project.Model.CartItem;
import com.ecommerce.project.Model.Product;
import com.ecommerce.project.Util.AuthUtil;
import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.repository.CartItemRepository;
import com.ecommerce.project.repository.CartRepository;
import com.ecommerce.project.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AuthUtil authUtil;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        Cart cart = createCart();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product" , "productId" , productId));

        CartItem cartItem = cartItemRepository.findCartItemByCartIdAndProductId(cart.getCartId() , product.getProductId());

        if(cartItem != null){
            throw new APIException("Product" + product.getProductName() + " already exists in the cart");
        }

        if(quantity <= 0){
            throw new APIException("Quantity must be greater than zero");
        }

        if(quantity > product.getQuantity()){
            throw new APIException("Quantity must be less than or equal to available stock" + product.getQuantity());
        }

        CartItem newCartItem = new CartItem();

        newCartItem.setCart(cart);
        newCartItem.setProduct(product);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());
        product.setQuantity(product.getQuantity());

        cartItemRepository.save(newCartItem);

        cart.setTotalPrice(cart.getTotalPrice() + (quantity * product.getSpecialPrice()));
        cartRepository.save(cart);

        CartDTO cartDTO = mapper.map(cart , CartDTO.class);

        List<ProductDTO>  productDTOS = cart.getCartItems().stream().map(item -> {
            ProductDTO productDTO = mapper.map(item.getProduct() , ProductDTO.class);
                    productDTO.setQuantity(item.getQuantity());
                    return productDTO;
        }).toList();

        cartDTO.setProducts(productDTOS);

        return cartDTO;
    }

    @Override
    public List<CartDTO> getAllCart() {
        List<Cart> carts = cartRepository.findAll();

        if(carts.isEmpty()) {
            throw new APIException("No cart present");
        }

        List<CartDTO> cartDTOS = carts.stream().
                map(cart -> {
                    CartDTO cartDTO = mapper.map(cart , CartDTO.class);
                    List<CartItem> cartItems = cart.getCartItems();
                    List<ProductDTO> productDTOS = cartItems.stream()
                            .map(cartItem -> {
                                ProductDTO productDTO = mapper.map(cartItem.getProduct(), ProductDTO.class);
                                productDTO.setQuantity(cartItem.getQuantity());
                                return productDTO;
                            }).collect(Collectors.toList());

                    cartDTO.setProducts(productDTOS);
                    return cartDTO;
                }).toList();

        return cartDTOS;
    }

    @Override
    public CartDTO getUserCart(String email, Long cartId) {
        Cart cart = cartRepository.findCartByEmailAndCartId(email , cartId);
        if(cart == null){
            throw new ResourceNotFoundException("Cart" , "CartId" , cartId);
        }

        CartDTO cartDTO = mapper.map(cart , CartDTO.class);

        List<ProductDTO> productDTOS = cart.getCartItems().stream()
                .map(cartItem -> {
                    ProductDTO productDTO = mapper.map(cartItem.getProduct(), ProductDTO.class);
                    productDTO.setQuantity(cartItem.getQuantity());
                    return productDTO;
                }).toList();
        cartDTO.setProducts(productDTOS);
        return cartDTO;
    }

    @Transactional
    @Override
    public CartDTO updateProductQuantity(Long prodId, Integer quantity) {

        String email = authUtil.getCurrentUserEmail();
        Cart userCart = cartRepository.findCartByEmail(email);
        long cartId = userCart.getCartId();

        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Cart" , "CartId" , cartId);
        });

        Product product = productRepository.findById(prodId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Product" , "productId" , prodId);
        });

        CartItem cartItem = cartItemRepository.findCartItemByCartIdAndProductId(cartId , prodId);

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setProductPrice(product.getPrice());
        cartItem.setDiscount(product.getDiscount());

        cart.setTotalPrice(cart.getTotalPrice() + (quantity * cartItem.getProductPrice()));
        cartRepository.save(cart);

        CartItem updateItem = cartItemRepository.save(cartItem);

        if(updateItem.getQuantity() == 0){
            cartItemRepository.deleteById(updateItem.getCartItemId());
        }

        CartDTO cartDTO = mapper.map(cart , CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDTO> productStream = cartItems.stream().map((item -> {
            ProductDTO productDTO = mapper.map(item.getProduct() , ProductDTO.class);
            productDTO.setQuantity(item.getQuantity());
            return productDTO;
        }));

        cartDTO.setProducts(productStream.toList());
        return cartDTO;
    }



    public Cart createCart(){
        Cart cart = cartRepository.findCartByEmail(authUtil.getCurrentUserEmail());
        if(cart != null){
            return cart;
        }
        Cart newCart = new Cart();
        newCart.setUser(authUtil.getLoggedInUser());
        newCart.setTotalPrice(0.0);
        return cartRepository.save(newCart);
    }

    @Transactional
    @Override
    public String deleteProductFromCart(Long cartId, Long prodId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new ResourceNotFoundException("Cart" , "cartId" , cartId));

        CartItem cartItem = cartItemRepository.findCartItemByCartIdAndProductId(cartId, prodId);

        if (cartItem == null) {
            throw new ResourceNotFoundException("Product", "productId", prodId);
        }

        cart.setTotalPrice(cart.getTotalPrice() -
                (cartItem.getProductPrice() * cartItem.getQuantity()));

        cartItemRepository.deleteCartItemByProductIdAndCartId(cartId, prodId);

        return "Product " + cartItem.getProduct().getProductName() + " removed from the cart !!!";
    }

    @Override
    public void updateProductInCarts(Long cartId, Long productId) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Cart" , "CartId" , cartId);
        });

        Product product = productRepository.findById(productId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Product" , "productId" , productId);
        });

        CartItem cartItem = cartItemRepository.findCartItemByCartIdAndProductId(cartId , productId);

        if(cartItem == null){
            throw new APIException("Product" + product.getProductName() + "is not available in the cart");
        }

        double cartPrice = cart.getTotalPrice() - cartItem.getQuantity() * cartItem.getProductPrice();

        cartItem.setProductPrice(product.getPrice());

        cart.setTotalPrice(cartPrice + cartItem.getProductPrice() * cartItem.getQuantity());

        cartItemRepository.save(cartItem);

    }
}
