package com.example.duan1.Models;

public class QLDT {
    private int madt;
    private String tendt;
    private String sao;
    private String dungluong;
    private int gia;
    private String image;


    public QLDT(String tendt, String sao, String dungluong, int gia, String image) {
        this.tendt = tendt;
        this.sao = sao;
        this.dungluong = dungluong;
        this.gia = gia;
        this.image = image;
    }

    public QLDT(int madt, String tendt, String sao, String dungluong, int gia, String image) {
        this.madt = madt;
        this.tendt = tendt;
        this.sao = sao;
        this.dungluong = dungluong;
        this.gia = gia;
        this.image = image;
    }

    public int getMadt() {
        return madt;
    }

    public void setMadt(int madt) {
        this.madt = madt;
    }

    public String getTendt() {
        return tendt;
    }

    public void setTendt(String tendt) {
        this.tendt = tendt;
    }

    public String getSao() {
        return sao;
    }

    public void setSao(String sao) {
        this.sao = sao;
    }

    public String getDungluong() {
        return dungluong;
    }

    public void setDungluong(String dungluong) {
        this.dungluong = dungluong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
