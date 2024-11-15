package com.example.duan1.Models;

public class ProductModels {
    private int img;
    private String name;
    private String price;

    public ProductModels(int img,String name, String price){
        this.img = img;
        this.name = name;
        this.price = price;

    }

    public int getImg() {
        return img;
    }



    public String getName() {
        return name;
    }



    public String getPrice() {
        return price;
    }

}
