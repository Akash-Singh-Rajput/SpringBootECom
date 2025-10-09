package com.ecommerce.project.security.services;

import com.ecommerce.project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
}
