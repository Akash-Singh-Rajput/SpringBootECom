package com.ecommerce.project.security.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {

    String username;
    String password;


}
