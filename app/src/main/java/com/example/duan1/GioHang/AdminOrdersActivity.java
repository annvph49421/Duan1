package com.example.duan1.GioHang;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.AdminOrdersAdapter;
import com.example.duan1.DAO.OrderDAO;
import com.example.duan1.Models.Order;
import com.example.duan1.R;

import java.util.List;

public class AdminOrdersActivity extends AppCompatActivity implements AdminOrdersAdapter.OnItemClickListener {

    private RecyclerView ordersRecyclerView;
    private AdminOrdersAdapter adapter;
    private List<Order> orders;
    private OrderDAO orderDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);

        // Khởi tạo RecyclerView và OrderDAO
        ordersRecyclerView = findViewById(R.id.recyclerViewOrders);
        orderDAO = new OrderDAO(this);

        // Lấy danh sách đơn hàng
        orders = orderDAO.getAllOrders();
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set adapter cho RecyclerView
        adapter = new AdminOrdersAdapter(this, orders, this);
        ordersRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onApproveClick(Order order) {
        // Cập nhật trạng thái đơn hàng khi phê duyệt
        order.setApprovalStatus("Đặt thành công");
        order.setStatus("Đang giao hàng");

        // Kiểm tra kết quả cập nhật
        int rowsAffected = orderDAO.updateOrder(order);
        if (rowsAffected > 0) {
            // Làm mới danh sách đơn hàng trong admin
            orders.clear();
            orders.addAll(orderDAO.getAllOrders());
            adapter.notifyDataSetChanged();

            // Gửi broadcast để thông báo cập nhật cho OrdersActivity
            Intent intent = new Intent("UPDATE_ORDERS");
            sendBroadcast(intent);

            Toast.makeText(this, "Đơn hàng đã được phê duyệt!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Có lỗi xảy ra khi phê duyệt đơn hàng!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRejectClick(Order order) {
        // Cập nhật trạng thái đơn hàng khi từ chối
        order.setApprovalStatus("Đặt thất bại");
        order.setStatus("Đã hủy");

        // Kiểm tra kết quả cập nhật
        int rowsAffected = orderDAO.updateOrder(order);
        if (rowsAffected > 0) {
            // Làm mới danh sách đơn hàng trong admin
            orders.clear();
            orders.addAll(orderDAO.getAllOrders());
            adapter.notifyDataSetChanged();

            // Gửi broadcast để thông báo cập nhật cho OrdersActivity
            Intent intent = new Intent("UPDATE_ORDERS");
            sendBroadcast(intent);

            Toast.makeText(this, "Đơn hàng đã bị từ chối!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Có lỗi xảy ra khi từ chối đơn hàng!", Toast.LENGTH_SHORT).show();
        }
    }


}
