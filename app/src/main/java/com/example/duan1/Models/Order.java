package com.example.duan1.Models;

import java.io.Serializable;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String address;
    private List<CartItem> cartItems;
    private int totalPrice;
    private String status;

    public Order(String address, List<CartItem> cartItems, int totalPrice, String status) {
        this.address = address;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }
}
