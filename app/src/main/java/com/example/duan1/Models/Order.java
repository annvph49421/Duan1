package com.example.duan1.Models;

public class Order {
    private int id;
    private String address;
    private String productDetails;
    private int totalPrice;
    private String status;

    public Order(int id, String address, String productDetails, int totalPrice, String status) {
        this.id = id;
        this.address = address;
        this.productDetails = productDetails;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }
}
