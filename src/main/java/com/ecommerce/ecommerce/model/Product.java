package com.ecommerce.ecommerce.model;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String detail;
    @Column
    private String image;
    @Column
    private double price;
    @Column
    private int quantity;

    @ManyToOne
    private User user;

    public Product() {
    }

    public Product(Long id, String name, String detail, String image, double price, int stock) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.image = image;
        this.price = price;
        this.quantity = stock;
    }

    public Product(Long id, String name, String detail, String image, double price, int quantity, User user) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.user = user;
    }
}
