package com.example.duan1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DAO.OrderDAO;
import com.example.duan1.GioHang.AdminOrdersActivity;
import com.example.duan1.Models.Order;
import com.example.duan1.R;

import java.util.List;

public class AdminOrdersAdapter extends RecyclerView.Adapter<AdminOrdersAdapter.ViewHolder> {
    private List<Order> orders;
    private Context context;

    public AdminOrdersAdapter(Context context, List<Order> orders, AdminOrdersActivity adminOrdersActivity) {
        this.context = context;
        this.orders = orders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.tvOrderAddress.setText("Địa chỉ: " + order.getAddress());
        holder.tvOrderTotalPrice.setText("Tổng cộng: " + order.getTotalPrice() + " đ");
        holder.tvOrderStatus.setText("Trạng thái: " + order.getStatus());
        holder.tvProductDetails.setText("Sản phẩm: " + order.getProductDetails());
        holder.tvOrderApprovalStatus.setText("Trạng thái phê duyệt: " + order.getApprovalStatus());

        // Xử lý nút phê duyệt
        holder.btnApprove.setOnClickListener(v -> {
            order.setApprovalStatus("Đặt thành công");
            order.setStatus("Đang giao hàng");
            updateOrderApprovalStatus(order);

            // Gửi broadcast để thông báo cập nhật cho OrdersActivity
            Intent intent = new Intent("UPDATE_ORDERS");
            context.sendBroadcast(intent);
        });

        // Xử lý nút từ chối
        holder.btnCancel.setOnClickListener(v -> {
            order.setApprovalStatus("Đặt thất bại");
            order.setStatus("Đã hủy");
            updateOrderApprovalStatus(order);

            // Gửi broadcast để thông báo cập nhật cho OrdersActivity
            Intent intent = new Intent("UPDATE_ORDERS");
            context.sendBroadcast(intent);
        });
    }


    private void updateOrderApprovalStatus(Order order) {
        OrderDAO orderDAO = new OrderDAO(context);
        orderDAO.updateOrderStatus(order.getOrderId(), order.getApprovalStatus());
        notifyDataSetChanged();  // Cập nhật lại danh sách đơn hàng
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderAddress, tvOrderTotalPrice, tvOrderStatus, tvProductDetails, tvOrderApprovalStatus;
        Button btnApprove, btnCancel;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrderAddress = itemView.findViewById(R.id.tvOrderAddress);
            tvOrderTotalPrice = itemView.findViewById(R.id.tvOrderTotalPrice);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvProductDetails = itemView.findViewById(R.id.tvProductDetails);
            tvOrderApprovalStatus = itemView.findViewById(R.id.tvOrderApprovalStatus);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
    // Interface để Activity hoặc Fragment có thể nhận sự kiện
    public interface OnItemClickListener {
        void onApproveClick(Order order);
        void onRejectClick(Order order);
    }
}

