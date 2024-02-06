package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public Optional<User> findById(Long id) {
        return iUserRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return iUserRepository.findByEmail(email);
    }
}
