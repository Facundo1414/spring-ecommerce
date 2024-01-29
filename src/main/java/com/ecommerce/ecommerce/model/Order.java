package com.ecommerce.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private Date dateCreated;
    private Date dateReceipt;
    private double total;

    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "order")
    private OrderDetail orderDetail;

    public Order() {
    }

    public Order(Long id, String number, Date dateCreated, Date dateReceipt) {
        this.id = id;
        this.number = number;
        this.dateCreated = dateCreated;
        this.dateReceipt = dateReceipt;
    }

    public Order(Long id, String number, Date dateCreated, Date dateReceipt, double total, User user) {
        this.id = id;
        this.number = number;
        this.dateCreated = dateCreated;
        this.dateReceipt = dateReceipt;
        this.total = total;
        this.user = user;
    }
}
