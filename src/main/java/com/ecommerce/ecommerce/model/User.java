package com.ecommerce.ecommerce.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String adress;
    private String phoneNumber;
    private String rol;
    private String password;

    public User() {
    }

    public User(Long id, String name, String username, String email, String adress, String phoneNumber, String rol, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.rol = rol;
        this.password = password;
    }
}
