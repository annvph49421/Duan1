package com.example.duan1.GioHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1.DAO.OrderDAO;
import com.example.duan1.Models.CartItem;
import com.example.duan1.Models.Order;
import com.example.duan1.R;

import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {
    private TextView addressTextView, productsTextView, totalPriceTextView;
    private Button btnPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        // Nhận dữ liệu từ CartActivity
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        List<CartItem> cartItems = (List<CartItem>) intent.getSerializableExtra("cartItems");

        // Gán thông tin địa chỉ và giỏ hàng vào các TextView
        addressTextView = findViewById(R.id.addressTextView);
        productsTextView = findViewById(R.id.productsTextView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        addressTextView.setText("Địa chỉ: " + address);

        // Hiển thị sản phẩm trong giỏ hàng
        StringBuilder productDetails = new StringBuilder();
        int totalPrice = 0;
        for (CartItem item : cartItems) {
            productDetails.append(item.getProductName())
                    .append(" - ")
                    .append(item.getQuantity())
                    .append(" x ")
                    .append(item.getPrice())
                    .append("đ\n");
            totalPrice += item.getTotalPrice();
        }
        productsTextView.setText(productDetails.toString());
        totalPriceTextView.setText("Tổng cộng: " + totalPrice + "đ");

        // Đặt hàng
        int finalTotalPrice = totalPrice;
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lưu đơn hàng và chuyển đến Admin
                placeOrder(address, cartItems, finalTotalPrice);
            }
        });
    }

    private void placeOrder(String address, List<CartItem> cartItems, int totalPrice) {
        // Lưu đơn hàng vào cơ sở dữ liệu hoặc gửi đến server
        OrderDAO orderDAO = new OrderDAO(this);
        Order order = new Order(address, cartItems, totalPrice, "Chờ phê duyệt");
        orderDAO.addOrder(order);

        // Chuyển đến màn hình Admin (hoặc admin sẽ nhận thông báo)
        Intent intent = new Intent(OrderDetailsActivity.this, AdminOrderActivity.class);
        startActivity(intent);
        finish(); // Đóng màn hình này
    }
}
