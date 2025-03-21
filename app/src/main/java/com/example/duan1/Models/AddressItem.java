package com.example.duan1.Models;

public class AddressItem {


    private String address;
    private  String name;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AddressItem(String address, String name, String phone) {
        this.address = address;
        this.name = name;
        this.phone = phone;
    }

    public AddressItem(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}