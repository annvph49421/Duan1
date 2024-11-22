package com.example.duan1.GioHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1.Adapter.CartAdapter;
import com.example.duan1.ChiTietSP.ctsp_ip;
import com.example.duan1.DAO.CartDAO;
import com.example.duan1.Home.Home;
import com.example.duan1.Models.CartItem;
import com.example.duan1.R;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ListView cartListView;
    private CartAdapter cartAdapter;
    private CartDAO cartDAO;
    private List<CartItem> cartItems;
    private TextView totalPriceTextView;
    ImageView btnBack_ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        btnBack_ip = findViewById(R.id. btnBack_ip);
        //nút thoát
        btnBack_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, Home.class));
            }
        });

        cartListView = findViewById(R.id.cartListView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);  // TextView hiển thị tổng giá trị
        cartDAO = new CartDAO(this);

        // Lấy dữ liệu giỏ hàng
        cartItems = cartDAO.getCartItems();

        // Tạo adapter và gán vào ListView
        cartAdapter = new CartAdapter(this, cartItems);
        cartListView.setAdapter(cartAdapter);

        // Cập nhật tổng giá trị giỏ hàng
        updateTotalPrice();
    }

    // Phương thức cập nhật tổng giá trị giỏ hàng
    public void updateTotalPrice() {
        int totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();  // Tính tổng giá trị giỏ hàng
        }
        totalPriceTextView.setText("Tổng cộng: " + totalPrice + "đ");  // Hiển thị tổng giá trị
    }
}