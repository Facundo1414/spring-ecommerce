package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Order;
import com.ecommerce.ecommerce.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService{
    @Autowired
    private IOrderRepository iOrderRepository;


    @Override
    public Order save(Order order) {
        return iOrderRepository.save(order);
    }
}
