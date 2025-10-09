package com.ecommerce.project.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "column_id")
    private Long id;

    @NotBlank
    @Size(min = 5 , message = "Street name must be at-least  5 characters")
    private String street;

    @NotBlank
    @Size(min = 2 , message = "Building name must be at-least  5 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 2 , message = "state name must be at-least  5 characters")
    private String state;

    @NotBlank
    @Size(min = 2  , message = "country name must be at-least  5 characters")
    private String country;

    @NotBlank
    @Size(min = 6  , message = "pincode name must be at-least  5 characters")
    private String pincode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();


}
