package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findById(Long id);
}
