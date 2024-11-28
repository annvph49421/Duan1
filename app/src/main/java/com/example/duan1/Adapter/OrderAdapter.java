package com.example.duan1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Models.Order;
import com.example.duan1.GioHang.OrderConfirmationActivity;
import com.example.duan1.Models.Order;
import com.example.duan1.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    private Context context;
    private List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.addressTextView.setText("Địa chỉ: " + order.getAddress());
        holder.totalPriceTextView.setText("Tổng tiền: " + order.getTotalPrice() + "đ");
        holder.statusTextView.setText("Trạng thái: " + order.getStatus());

        // Xử lý khi nhấn vào item để xem chi tiết đơn hàng
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderConfirmationActivity.class);
            intent.putExtra("orderId", order.getId()); // Truyền ID đơn hàng
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView addressTextView, totalPriceTextView, statusTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }
}
