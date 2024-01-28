package com.ecommerce.ecommerce.model;

import lombok.Data;

import java.util.Date;

@Data
public class Ordenty {
    private Long id;
    private String number;
    private Date dateCreated;
    private Date dateReceipt;

    public Ordenty() {
    }

    public Ordenty(Long id, String number, Date dateCreated, Date dateReceipt) {
        this.id = id;
        this.number = number;
        this.dateCreated = dateCreated;
        this.dateReceipt = dateReceipt;
    }
}
