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
    private String nombre;
    private double cantidad;
    private double precio;
    private double total;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    private Product product;

    public OrderDetail() {
    }




}
