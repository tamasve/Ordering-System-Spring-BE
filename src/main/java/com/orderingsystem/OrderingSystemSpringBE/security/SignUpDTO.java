package com.orderingsystem.OrderingSystemSpringBE.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {

    private String username;

    private String email;

    private String password;

}
