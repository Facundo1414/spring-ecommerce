package com.ecommerce.ecommerce.model;

import lombok.Data;

@Data
public class OrderDetail {
    private Long id;
    private String name;
    private double quantity;
    private double price;
    private double total;

    public OrderDetail() {
    }

    public OrderDetail(Long id, String name, double quantity, double price, double total) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }
}
