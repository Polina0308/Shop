package com.example.shop.service;

import com.example.shop.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService { //security

boolean save(UserDTO userDTO);
}
