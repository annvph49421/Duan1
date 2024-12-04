package com.example.duan1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.DAO.OrderDAO;
import com.example.duan1.Models.CartItem;
import com.example.duan1.Models.Order;
import com.example.duan1.R;

import java.text.DecimalFormat;
import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order> {

    private Context context;
    private List<Order> orders;

    public OrderAdapter(Context context, List<Order> orders) {
        super(context, 0, orders);
        this.context = context;
        this.orders = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        }

        // Lấy thông tin đơn hàng tại vị trí hiện tại
        Order order = orders.get(position);

        // Ánh xạ các thành phần giao diện
        TextView tvOrderAddress = convertView.findViewById(R.id.tvOrderAddress);
        TextView tvOrderTotalPrice = convertView.findViewById(R.id.tvOrderTotalPrice);
        TextView tvOrderStatus = convertView.findViewById(R.id.tvOrderStatus);
        TextView tvProductDetails = convertView.findViewById(R.id.tvProductDetails);
        TextView tvOrderApprovalStatus = convertView.findViewById(R.id.tvOrderApprovalStatus);
        TextView tvOrderTime = convertView.findViewById(R.id.tvOrderTime);
        Button btnDeleteOrder = convertView.findViewById(R.id.btnDeleteOrder);

        // Thiết lập giá trị cho các TextView
        tvOrderAddress.setText("Địa chỉ: " + order.getAddress());
        tvOrderTotalPrice.setText("Tổng cộng: " + order.getTotalPrice() + " đ");
        tvOrderStatus.setText("Trạng thái: " + order.getStatus());
        tvProductDetails.setText("Sản phẩm: " + order.getProductDetails());
        tvOrderApprovalStatus.setText("Trạng thái phê duyệt: " + order.getApprovalStatus());  // Hiển thị trạng thái
        //tvOrderTime.setText("Thời gian đặt hàng: " + order.getOrderTime());  // Hiển thị thời gian đặt hàng
        btnDeleteOrder.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xác nhận");
            builder.setMessage("Bạn có chắc chắn muốn xóa đơn hàng này?");
            builder.setPositiveButton("Xóa", (dialog, which) -> {
                // Lấy ID của đơn hàng cần xóa
                int orderId = order.getOrderId();

                // Gọi DAO để xóa đơn hàng
                OrderDAO  orderDAO = new OrderDAO(context);
                int rowsDeleted = orderDAO.deleteOrder(orderId);

                if (rowsDeleted > 0) {
                    // Xóa thành công, cập nhật lại danh sách
                    orders.remove(position); // Loại bỏ đơn hàng khỏi danh sách
                    notifyDataSetChanged(); // Thông báo adapter cập nhật dữ liệu
                    Toast.makeText(context, "Đơn hàng đã được xóa", Toast.LENGTH_SHORT).show();
                } else {
                    // Xóa thất bại
                    Toast.makeText(context, "Không thể xóa đơn hàng. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Hủy", null);
            builder.show();
        });

        return convertView;
    }
}