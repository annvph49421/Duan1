package com.example.duan1.Models;

public class ProductModels {
    private int img;
    private String name;
    private String price;
    private String moTa;
    private String rating;

    public ProductModels(int img, String name, String price, String moTa, String rating) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.moTa = moTa;
        this.rating = rating;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
