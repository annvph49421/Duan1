package com.example.duan1.SQLite;

// TopPhone.java
public class TopPhone {
    private String productName;
    private int soldQuantity;

    public TopPhone(String productName, int soldQuantity) {
        this.productName = productName;
        this.soldQuantity = soldQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }
}
