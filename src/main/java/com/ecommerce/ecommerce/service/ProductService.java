package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Product;

import java.util.Optional;

public interface ProductService {
    public Product save(Product product);
    public Optional<Product> get(Long id);
    public void update(Product product);
    public void delete(Long id);
}
