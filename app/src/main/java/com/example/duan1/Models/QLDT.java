package com.example.duan1.Models;

public class QLDT {
    private int madt;
    private String tendt;
    private Double sao;
    private String dungluong;
    private int gia;

    public QLDT(int madt, String tendt, Double sao, String dungluong, int gia) {
        this.madt = madt;
        this.tendt = tendt;
        this.sao = sao;
        this.dungluong = dungluong;
        this.gia = gia;
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

    public Double getSao() {
        return sao;
    }

    public void setSao(Double sao) {
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
}
