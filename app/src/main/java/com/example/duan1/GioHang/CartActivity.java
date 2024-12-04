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
import com.example.duan1.DAO.OrderDAO;
import com.example.duan1.Models.CartItem;
import com.example.duan1.Models.Order;
import com.example.duan1.R;

import java.text.DecimalFormat;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ListView cartListView;
    private CartAdapter cartAdapter;
    private CartDAO cartDAO;
    private List<CartItem> cartItems;
    private TextView totalPriceTextView;
    private ImageView btnBack, btnAddAddress, btnEditAddress;
    private TextView addressTextView;
    private Button btnOrder;
    private String address;
    private OrderDAO orderDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Ánh xạ các thành phần giao diện
        btnBack = findViewById(R.id.btnBack_ip);
        cartListView = findViewById(R.id.cartListView);
        totalPriceTextView = findViewById(R.id.tvTotalPrice);
        btnOrder = findViewById(R.id.btndathang);
        addressTextView = findViewById(R.id.addressTextView);
        btnAddAddress = findViewById(R.id.btnAddAddress);
        btnEditAddress = findViewById(R.id.btnsua);

        cartDAO = new CartDAO(this);
        orderDAO = new OrderDAO(this);

        // Lấy danh sách giỏ hàng từ cơ sở dữ liệu
        loadCartItems();

        // Hiển thị địa chỉ nhận hàng
        loadAddress();

        // Xử lý sự kiện
        setupListeners();
    }

    private void loadCartItems() {
        cartItems = cartDAO.getCartItems();
        cartAdapter = new CartAdapter(this, cartItems);
        cartListView.setAdapter(cartAdapter);
        updateTotalPrice();
    }

    private void loadAddress() {
        address = cartDAO.getAddress();
        if (address != null) {
            addressTextView.setText(address);
        } else {
            addressTextView.setText("Thêm địa chỉ nhận hàng");
        }
    }

    private void setupListeners() {
        // Nút quay lại
        btnBack.setOnClickListener(v -> onBackPressed());

        // Thêm địa chỉ
        btnAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, AddAddressActivity.class);
            startActivityForResult(intent, 1); // Request code = 1
        });

        // Sửa địa chỉ
        btnEditAddress.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, EditAddressActivity.class);
            startActivityForResult(intent, 1); // Request code = 1
        });

        // Đặt hàng
        btnOrder.setOnClickListener(v -> placeOrder());
    }

    // Cập nhật tổng giá trị giỏ hàng và hiển thị
    public void updateTotalPrice() {
        int totalPrice = calculateTotalPrice();
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        totalPriceTextView.setText("Tổng cộng: " + decimalFormat.format(totalPrice));
    }

    // Tính toán tổng giá trị của giỏ hàng
    private int calculateTotalPrice() {
        int totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    // Xử lý logic đặt hàng
    private void placeOrder() {
        if (cartItems.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng của bạn đang trống", Toast.LENGTH_SHORT).show();
            return;
        }

        if (address == null || address.isEmpty()) {
            Toast.makeText(this, "Vui lòng thêm địa chỉ nhận hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo chi tiết đơn hàng từ giỏ hàng
        StringBuilder productDetails = new StringBuilder();
        for (CartItem item : cartItems) {
            productDetails.append(item.getProductName())
                    .append(" - Số lượng: ").append(item.getQuantity())
                    .append(" x ").append(item.getPrice())
                    .append("đ\n");
        }

        // Tạo đơn hàng
        int totalPrice = calculateTotalPrice();
        String orderStatus = "Chờ phê duyệt";

        // Thêm đơn hàng vào cơ sở dữ liệu
        Order order = new Order(address, totalPrice, orderStatus, productDetails.toString(), 0);
        long orderId = orderDAO.addOrder(order); // Thêm đơn hàng và lấy ID đơn hàng

        if (orderId > 0) {
            // Làm sạch giỏ hàng
            cartDAO.clearCart();
            cartItems.clear();
            cartAdapter.notifyDataSetChanged();

            // Thông báo và chuyển sang màn hình danh sách đơn hàng
            Toast.makeText(this, "Đơn hàng của bạn đã được ghi nhận", Toast.LENGTH_SHORT).show();
            navigateToOrdersScreen();
        } else {
            Toast.makeText(this, "Đặt hàng thất bại. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
        }
    }

    // Điều hướng đến màn hình danh sách đơn hàng
    private void navigateToOrdersScreen() {
        Intent intent = new Intent(CartActivity.this, OrdersActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Lấy dữ liệu địa chỉ từ Intent
            String name = data.getStringExtra("name");
            String address = data.getStringExtra("address");
            String phone = data.getStringExtra("phone");

            if (name != null && address != null && phone != null) {
                // Gộp địa chỉ và lưu vào cơ sở dữ liệu
                String fullAddress = address + " - " + phone + "\n" + name;
                cartDAO.addAddress(fullAddress);

                // Hiển thị địa chỉ lên giao diện
                addressTextView.setText(fullAddress);
                Toast.makeText(this, "Địa chỉ đã được cập nhật", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Không thể cập nhật địa chỉ. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}