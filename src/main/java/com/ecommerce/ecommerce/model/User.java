package com.ecommerce.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String adress;
    @Column
    private String phoneNumber;
    @Column
    private String rol;
    @Column
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User() {
    }

    public User(Long id, String name, String username, String email, String adress, String phoneNumber, String rol, String password, List<Product> products, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.rol = rol;
        this.password = password;
        this.products = products;
        this.orders = orders;
    }
}
