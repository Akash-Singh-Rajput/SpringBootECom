package com.ecommerce.project.Util;

import com.ecommerce.project.Exception.APIException;
import com.ecommerce.project.Model.User;
import com.ecommerce.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    @Autowired
    UserRepository userRepository;

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated() || authentication.getName().equalsIgnoreCase("anonymousUser")){
            throw new UsernameNotFoundException("Please login first!!");
        }
        User user = userRepository.findByUsername(authentication.getName()).
                orElseThrow(() -> new UsernameNotFoundException("User not found: " + authentication.getName()));
        return user;

    }
    public String getCurrentUserEmail() {
        User user = getLoggedInUser();
        return user.getEmail();
    }
}
