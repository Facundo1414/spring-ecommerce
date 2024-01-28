package com.ecommerce.ecommerce.model;


import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private String detail;
    private String image;
    private double price;
    private int quantity;

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
}
