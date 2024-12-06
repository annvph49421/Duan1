package com.example.duan1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DAO.OrderDAO;
import com.example.duan1.GioHang.AdminOrdersActivity;
import com.example.duan1.Models.Order;
import com.example.duan1.R;

import java.util.List;

public class AdminOrdersAdapter extends RecyclerView.Adapter<AdminOrdersAdapter.ViewHolder> {
    private List<Order> orders;
    private Context context;

    public AdminOrdersAdapter(Context context, List<Order> orders) {
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
            // Cập nhật trạng thái phê duyệt và đơn hàng
            order.setApprovalStatus("Đặt thành công");
            order.setStatus("Đang giao hàng");
            updateOrderApprovalStatus(order);

            // Gửi broadcast để thông báo cập nhật cho OrdersActivity
            Intent intent = new Intent("UPDATE_ORDERS");
            context.sendBroadcast(intent);
        });


    }
    private void confirmOrder(Order order) {
        // Cập nhật trạng thái phê duyệt và đơn hàng
        order.setApprovalStatus("Đặt thành công");
        order.setStatus("Đang giao hàng");

        // Cập nhật trạng thái trong cơ sở dữ liệu
        OrderDAO orderDAO = new OrderDAO(context);
        orderDAO.updateOrderStatus(order.getOrderId(), order.getApprovalStatus());

        // Cập nhật lại dữ liệu trong adapter
        int position = orders.indexOf(order);
        if (position != -1) {
            notifyItemChanged(position);  // Thông báo thay đổi cho item đã thay đổi
        }

        // Gửi broadcast để thông báo cập nhật cho OrdersActivity
        Intent intent = new Intent("UPDATE_ORDERS");
        context.sendBroadcast(intent);

        // Thông báo cho người dùng
        Toast.makeText(context, "Đơn hàng đã được xác nhận!", Toast.LENGTH_SHORT).show();
    }

    private void updateOrderApprovalStatus(Order order) {
        // Khởi tạo OrderDAO và cập nhật trạng thái phê duyệt
        OrderDAO orderDAO = new OrderDAO(context);
        orderDAO.updateOrderStatus(order.getOrderId(), order.getApprovalStatus());

        // Cập nhật lại dữ liệu và thông báo thay đổi cho adapter
        int position = orders.indexOf(order);  // Lấy vị trí của đơn hàng đã thay đổi
        if (position != -1) {
            notifyItemChanged(position);  // Chỉ thông báo thay đổi cho item đã thay đổi
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderAddress, tvOrderTotalPrice, tvOrderStatus, tvProductDetails, tvOrderApprovalStatus;
        Button btnApprove, btnConfirm; // Chỉ giữ lại nút phê duyệt

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrderAddress = itemView.findViewById(R.id.tvOrderAddress);
            tvOrderTotalPrice = itemView.findViewById(R.id.tvOrderTotalPrice);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvProductDetails = itemView.findViewById(R.id.tvProductDetails);
            tvOrderApprovalStatus = itemView.findViewById(R.id.tvOrderApprovalStatus);
            btnApprove = itemView.findViewById(R.id.btnApprove);  // Chỉ giữ lại nút phê duyệt

        }
    }

    // Interface để Activity hoặc Fragment có thể nhận sự kiện
    public interface OnItemClickListener {
        void onApproveClick(Order order);
        void onRejectClick(Order order);
    }
}
