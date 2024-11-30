package com.example.duan1.Models;

public class CartItem {
    private String productName;
    private int quantity;
    private int price;
    private int imageResId;

    public CartItem(String productName, int quantity, int price, int imageResId) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price * quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    // Phương thức tính tổng giá trị của sản phẩm (giá x số lượng)
    public int getTotalPrice() {
        return price * quantity;
    }



}