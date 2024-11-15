package com.example.duan1.Models;

public class MenuItem {
    private int icon;
    private String title;

    public MenuItem(int icon,String title){
        this.icon = icon;
        this.title = title;
    }

    public int getIcon(){
        return icon;

    }
    public String getTitle(){
        return title;
    }
}
