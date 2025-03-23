package com.example.recibodeventademo.data;

import java.io.Serializable;

public class Product implements Serializable {
    private String code;
    private String description;
    private double price;
    private int quantity;

    public Product(String code, String description, double price) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.quantity = 1;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return this.price * this.quantity;
    }
}
