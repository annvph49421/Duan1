package com.example.duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        // Thiết lập giá trị cho các TextView
        tvOrderAddress.setText("Địa chỉ: " + order.getAddress());
        tvOrderTotalPrice.setText("Tổng cộng: " + order.getTotalPrice() + " đ");
        tvOrderStatus.setText("Trạng thái: " + order.getStatus());
        tvProductDetails.setText("Sản phẩm: " + order.getProductDetails());
        tvOrderApprovalStatus.setText("Trạng thái phê duyệt: " + order.getApprovalStatus());  // Hiển thị trạng thái

        return convertView;
    }
}