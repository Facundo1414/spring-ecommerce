package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findById(Long id);
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findAll();

}
