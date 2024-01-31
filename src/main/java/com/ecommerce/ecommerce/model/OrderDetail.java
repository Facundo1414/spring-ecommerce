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

    @OneToOne
    private Order order;

    @ManyToOne
    private Product product;

    public OrderDetail() {
    }

    public OrderDetail(Long id, String nombre, double cantidad, double precio, double total, Order order, Product product) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
        this.order = order;
        this.product = product;
    }


}
