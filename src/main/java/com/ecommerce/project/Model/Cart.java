package com.ecommerce.project.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart" , cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE} , orphanRemoval = true)
            /*
            * here cartItem is a weak entity and it's associated with cart,
            * if cart doesn't exist cartItem will also be deleted from the cartItem table
            * Cascade.REMOVE make sure that if cart is deleted and all it's cartItem will
            * be deleted from the cartItem table
            *
            * orphanRemoval = true, when you delete a particular cartItem from a cart
            * then it deletes that cartItem from the cartItem table;
            */
    List<CartItem> cartItems = new ArrayList<>();

    private double totalPrice;
}
