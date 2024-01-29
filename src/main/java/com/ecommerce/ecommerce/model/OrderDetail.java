package com.ecommerce.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orderDetails")
@Data
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double quantity;
    private double price;
    private double total;

    @OneToOne
    private Order order;

    @ManyToOne
    private Product product;

    public OrderDetail() {
    }

    public OrderDetail(Long id, String name, double quantity, double price, double total, Order order, Product product) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.order = order;
        this.product = product;
    }
}
