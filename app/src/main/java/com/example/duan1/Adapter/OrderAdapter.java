package com.example.duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DAO.OrderDAO;
import com.example.duan1.Models.Order;
import com.example.duan1.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orders;
    private OrderDAO orderDAO;

    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
        this.orderDAO = new OrderDAO(context); // Khởi tạo OrderDAO
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.tvOrderDetails.setText(order.getProductDetails());
        holder.tvOrderAddress.setText(order.getAddress());
        holder.tvOrderStatus.setText(order.getStatus());

        // Xử lý sự kiện nút Xác nhận
        holder.btnConfirmOrder.setOnClickListener(v -> {
            order.setStatus("Đã xác nhận");
            notifyItemChanged(position); // Cập nhật lại vị trí đơn hàng trong RecyclerView

            // Cập nhật trạng thái đơn hàng trong database
            orderDAO.updateOrderStatus(order.getOrderId(), "Đã xác nhận");

            // Thông báo cho người dùng
            Toast.makeText(context, "Đơn hàng đã được xác nhận", Toast.LENGTH_SHORT).show();
        });

        // Xử lý sự kiện nút Hủy
        holder.btnCancelOrder.setOnClickListener(v -> {
            order.setStatus("Đã hủy");
            notifyItemChanged(position); // Cập nhật lại vị trí đơn hàng trong RecyclerView

            // Cập nhật trạng thái đơn hàng trong database
            orderDAO.updateOrderStatus(order.getOrderId(), "Đã hủy");

            // Thông báo cho người dùng
            Toast.makeText(context, "Đơn hàng đã bị hủy", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderDetails, tvOrderAddress, tvOrderStatus;
        Button btnConfirmOrder, btnCancelOrder;

        public OrderViewHolder(View itemView) {
            super(itemView);
            tvOrderDetails = itemView.findViewById(R.id.tvOrderDetails);
            tvOrderAddress = itemView.findViewById(R.id.tvOrderAddress);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            btnConfirmOrder = itemView.findViewById(R.id.btnConfirmOrder);
            btnCancelOrder = itemView.findViewById(R.id.btnCancelOrder);
        }
    }
}
