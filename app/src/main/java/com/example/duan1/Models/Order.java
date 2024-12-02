package com.example.duan1.Models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private String address;
    private int totalPrice;
    private String status;
    private String productDetails;
    private List<CartItem> cartItems;
    private String approvalStatus;


    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Order(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Order(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Order(String address, int orderId, String productDetails, String status, int totalPrice) {
        this.address = address;
        this.orderId = orderId;
        this.productDetails = productDetails;
        this.status = status;
        this.totalPrice = totalPrice;
        if (cartItems == null) {
            this.cartItems = new ArrayList<>();
        } else {
            this.cartItems = cartItems;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

}