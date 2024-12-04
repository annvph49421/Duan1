package com.example.duan1.GioHang;

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
        // Cập nhật trạng thái đơn hàng thành "Đặt thành công"
        order.setApprovalStatus("Đặt thành công");
        order.setStatus("Đang giao hàng");

        // Cập nhật lại cơ sở dữ liệu
        orderDAO.updateOrder(order);

        // Cập nhật lại danh sách và notify adapter cho admin
        orders.clear();
        orders.addAll(orderDAO.getAllOrders());
        adapter.notifyDataSetChanged();

        // Cập nhật lại danh sách đơn hàng ở phía người dùng
        updateUserOrders();

        Toast.makeText(this, "Đơn hàng đã được phê duyệt!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRejectClick(Order order) {
        // Cập nhật trạng thái đơn hàng thành "Đặt thất bại"
        order.setApprovalStatus("Đặt thất bại");
        order.setStatus("Đã hủy");

        // Cập nhật lại cơ sở dữ liệu
        orderDAO.updateOrder(order);

        // Cập nhật lại danh sách và notify adapter cho admin
        orders.clear();
        orders.addAll(orderDAO.getAllOrders());
        adapter.notifyDataSetChanged();

        // Cập nhật lại danh sách đơn hàng ở phía người dùng
        updateUserOrders();

        Toast.makeText(this, "Đơn hàng đã bị từ chối!", Toast.LENGTH_SHORT).show();
    }

    // Phương thức cập nhật đơn hàng cho người dùng
    private void updateUserOrders() {
        // Lấy lại danh sách đơn hàng mới cho người dùng
        List<Order> userOrders = orderDAO.getAllOrders();

        // Tạo lại adapter cho ListView người dùng (hoặc RecyclerView nếu bạn dùng)
        // Giả sử bạn có một ListView cho đơn hàng người dùng:
        OrdersActivity orderActivity = new OrdersActivity();
        orderActivity.updateOrderList(userOrders);  // Gọi phương thức cập nhật danh sách đơn hàng ở phía người dùng
    }
}
