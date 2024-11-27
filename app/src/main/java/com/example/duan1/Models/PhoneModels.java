package com.example.duan1.Models;

public class PhoneModels {
    private int image;
    private String namePhone;
    private double rating;
    private String price;
    private String moTa;

    public PhoneModels(int image, String namePhone, double rating, String price, String moTa) {
        this.image = image;
        this.namePhone = namePhone;
        this.rating = rating;
        this.price = price;
        this.moTa = moTa;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return namePhone;
    }

    public void setNamePhone(String namePhone) {
        this.namePhone = namePhone;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
