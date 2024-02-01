package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    public Product save(Product product);
    public Optional<Product> get(Long id);
    public void update(Product product);
    public void delete(Long id);
    public List<Product> findAll();
}
