package com.example.duan1.ManHinhTT;

import android.os.Bundle;

import androidx.appcompat.view.menu.MenuAdapter;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.example.duan1.Models.MenuItem;

import com.example.duan1.Adapter.Adapter_TTCaNhan;
import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;


public class TTCaNhan extends Fragment {

    private RecyclerView menuRecyclerView;
    private Adapter_TTCaNhan menuAdapter;
    private List<MenuItem> menuItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_t_t_ca_nhan, container, false);




        menuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initializeMenuItems();
        menuAdapter = new Adapter_TTCaNhan(menuItems);
        menuRecyclerView.setAdapter(menuAdapter);

        return view;
    }

    private void initializeMenuItems() {
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.drawable.icon_ttcn_1, "Thông tin cá nhân"));
        menuItems.add(new MenuItem(R.drawable.icon_ttcn_1, "Địa chỉ"));
        menuItems.add(new MenuItem(R.drawable.icon_ttcn_1, "Giỏ hàng"));
        menuItems.add(new MenuItem(R.drawable.icon_ttcn_1, "Yêu thích"));
        menuItems.add(new MenuItem(R.drawable.icon_ttcn_1, "Thông báo"));
        menuItems.add(new MenuItem(R.drawable.icon_ttcn_1, "Phương thức thanh toán"));
        menuItems.add(new MenuItem(R.drawable.icon_ttcn_1, "FAQs"));
        menuItems.add(new MenuItem(R.drawable.icon_ttcn_1, "User Reviews"));
        menuItems.add(new MenuItem(R.drawable.icon_ttcn_1, "Cài đặt"));
        menuItems.add(new MenuItem(R.drawable.icon_ttcn_1, "Đăng xuất"));
    }
}