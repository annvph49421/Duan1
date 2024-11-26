package com.example.duan1.GioHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.Adapter.CartAdapter;
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
    private TextView addressTextView;

    private ImageView btnAddAddress;
    Button btndathang;
    private String address;

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
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        btnAddAddress = findViewById(R.id.btnAddAddress);
        addressTextView = findViewById(R.id.addressTextView);
        btndathang = findViewById(R.id.btndathang);

    // TextView hiển thị tổng giá trị
        cartDAO = new CartDAO(this);

        // Lấy dữ liệu giỏ hàng
        cartItems = cartDAO.getCartItems();

        // Tạo adapter và gán vào ListView
        cartAdapter = new CartAdapter(this, cartItems);
        cartListView.setAdapter(cartAdapter);

        // Cập nhật tổng giá trị giỏ hàng
        updateTotalPrice();
        // Nếu có địa chỉ, hiển thị nó
        if (address != null) {
            addressTextView.setText(address);
            btnAddAddress.setVisibility(View.GONE);  // Ẩn nút thêm địa chỉ nếu đã có
        } else {
            addressTextView.setText("Thêm địa chỉ nhận hàng");
        }

        // Xử lý sự kiện thêm địa chỉ
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở màn hình thêm địa chỉ
                Intent intent = new Intent(CartActivity.this, AddAddressActivity.class);
                startActivityForResult(intent, 1);  // Request code = 1
            }
        });


        // Sự kiện khi nhấn nút "Đặt Hàng"
        btndathang.setOnClickListener(v -> {
            // Kiểm tra nếu giỏ hàng có sản phẩm và người dùng đã nhập địa chỉ
//            if (cartItems.isEmpty()) {
//                Toast.makeText(CartActivity.this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
//            } else {
//                // Nếu có sản phẩm, chuyển đến màn hình đơn hàng
//                Intent intent = new Intent(CartActivity.this, OrderConfirmationActivity.class);
//                // Thêm dữ liệu giỏ hàng và địa chỉ vào Intent (nếu có địa chỉ)
//                String address = getAddress(); // Địa chỉ đã được lưu
//                if (address != null) {
//                    intent.putExtra("address", address);
//                    intent.putExtra("totalPrice", getTotalPrice());
//                }



        });
    }

    // Phương thức cập nhật tổng giá trị giỏ hàng
    public void updateTotalPrice() {
        int totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();  // Tính tổng giá trị giỏ hàng
        }
        totalPriceTextView.setText("Tổng cộng: " + totalPrice + "đ");  // Hiển thị tổng giá trị
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Nhận địa chỉ từ AddAddressActivity
            String name = data.getStringExtra("name");
            String address = data.getStringExtra("address");
            String phone = data.getStringExtra("phone");

            // Cập nhật địa chỉ lên CartActivity
            addressTextView.setText("Tên: " + name + "\nĐịa chỉ: " + address + "\nSĐT: " + phone);
        }
    }
}