package com.example.duan1.Models;

import java.util.List;

public class Order {
    private int orderId;
    private String address;
    private int totalPrice;
    private String status;
    private List<CartItem> cartItems;  // Danh sách sản phẩm trong đơn hàng
    private String productDetails;  // Chi tiết sản phẩm trong đơn hàng



    public Order(String address, int totalPrice, String status, List<CartItem> cartItems) {
        this.address = address;
        this.totalPrice = totalPrice;
        this.status = status;
        this.cartItems = cartItems;
    }

    public Order(int orderId, String address, int totalPrice, String status, List<CartItem> cartItems, String productDetails) {
        this.orderId = orderId;
        this.address = address;
        this.totalPrice = totalPrice;
        this.status = status;
        this.cartItems = cartItems;
        this.productDetails = productDetails;
    }

    // Getters và Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String generateProductDetails() {
        StringBuilder details = new StringBuilder();

        for (CartItem item : cartItems) {
            details.append(item.getProductName())
                    .append(" (x")
                    .append(item.getQuantity())
                    .append(") - ")
                    .append(item.getPrice())
                    .append("đ\n");
        }

        return details.toString();
    }
}
