package com.example.duan1.GioHang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1.Adapter.OrderAdapter;

import com.example.duan1.DAO.OrderDAO;
import com.example.duan1.Models.Order;
import com.example.duan1.R;

import java.text.DecimalFormat;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    private ListView orderListView;
    private OrderAdapter orderAdapter;
    private OrderDAO orderDAO;
    private List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        // Khởi tạo các thành phần giao diện
        orderListView = findViewById(R.id.orderListView);

        // Khởi tạo DAO và lấy dữ liệu đơn hàng
        orderDAO = new OrderDAO(this);
        orders = orderDAO.getAllOrders();

        // Set Adapter cho ListView
        orderAdapter = new OrderAdapter(this, orders);
        orderListView.setAdapter(orderAdapter);
    }
}
