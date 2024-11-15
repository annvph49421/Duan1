package com.example.duan1.Models;

public class PhoneModels {
    private int image;
    private String namePhone;
    private double rating;
    private String price;

    public PhoneModels(int image, String name, double rating, String price) {
        this.image = image;
        this.namePhone = name;
        this.rating = rating;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return namePhone;
    }

    public double getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }
}
