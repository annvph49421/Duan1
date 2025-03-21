package com.example.duan1.GioHang;

import android.content.Context;
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

        // Kiểm tra nếu có đơn hàng mới được gửi qua Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("newOrder")) {
            Order newOrder = (Order) intent.getSerializableExtra("newOrder");
            if (newOrder != null) {
                addNewOrder(newOrder);
            }
        }
    }

    // Phương thức thêm đơn hàng mới vào đầu danh sách
    public void addNewOrder(Order newOrder) {
        // Thêm đơn hàng mới vào đầu danh sách
        orders.add(0, newOrder);

        // Thông báo adapter để cập nhật giao diện
        orderAdapter.notifyDataSetChanged();
    }

    // Phương thức này sẽ được gọi từ AdminOrdersActivity để cập nhật lại danh sách đơn hàng
    public void updateOrderList(List<Order> updatedOrders) {
        // Cập nhật lại danh sách đơn hàng
        orders.clear();
        orders.addAll(updatedOrders);

        // Thông báo adapter để cập nhật giao diện
        orderAdapter.notifyDataSetChanged();
    }
}
