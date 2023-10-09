package com.example.shop.dao;

import com.example.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User FindFirstByName(String name);

}
