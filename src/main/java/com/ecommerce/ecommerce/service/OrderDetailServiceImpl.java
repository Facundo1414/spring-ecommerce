package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.OrderDetail;
import com.ecommerce.ecommerce.repository.IOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService{
    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }


}
