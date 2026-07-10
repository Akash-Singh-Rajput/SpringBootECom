package com.ecommerce.project.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "orders ")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(nullable = false)
    String email;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST }, orphanRemoval = true)
    private List<OrderItem> orderItemList;

    private Double totalAmount;
    private LocalDate orderDate;
    private String orderStatus;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne(cascade = {CascadeType.PERSIST , CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    private Address address;
}
