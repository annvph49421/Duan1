package com.example.duan1.GioHang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.Adapter.CartAdapter;
import com.example.duan1.DAO.CartDAO;
import com.example.duan1.Models.CartItem;
import com.example.duan1.Models.Order;
import com.example.duan1.R;
import com.example.duan1.SQLite.CartDatabaseHelper;

import java.text.DecimalFormat;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ListView cartListView;
    private CartAdapter cartAdapter;
    private CartDAO cartDAO;
    private List<CartItem> cartItems;
    private TextView totalPriceTextView;
    private ImageView btnBack,btnAddAddress,btnsua;
    private TextView addressTextView;
    private Button btnOrder;
    private String address;
    SQLiteOpenHelper dbHelper;

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
        btnsua = findViewById(R.id.btnsua);

        cartDAO = new CartDAO(this);

        // Lấy danh sách giỏ hàng từ cơ sở dữ liệu
        cartItems = cartDAO.getCartItems();
        cartAdapter = new CartAdapter(this, cartItems);
        cartListView.setAdapter(cartAdapter);

        // Cập nhật tổng giá trị giỏ hàng
        updateTotalPrice();

        // Hiển thị địa chỉ nhận hàng
        address = cartDAO.getAddress();
        if (address != null) {
            addressTextView.setText(address);
        } else {
            addressTextView.setText("Thêm địa chỉ nhận hàng");
        }

        // Xử lý nút quay lại
        btnBack.setOnClickListener(v -> onBackPressed());
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở màn hình thêm địa chỉ
                Intent intent = new Intent(CartActivity.this, AddAddressActivity.class);
                startActivityForResult(intent, 1);  // Request code = 1
            }
        });
        btnsua.setOnClickListener(v -> {
            // Mở EditAddressActivity để sửa địa chỉ
            Intent intent = new Intent(CartActivity.this, EditAddressActivity.class);
            startActivityForResult(intent, 1); // Request code = 1
        });

        // Xử lý nút Đặt Hàng
        btnOrder.setOnClickListener(v -> handleOrderPlacement());
    }

    /**
     * Cập nhật tổng giá trị giỏ hàng và hiển thị.
     */
    public void updateTotalPrice() {
        int totalPrice = calculateTotalPrice();
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        totalPriceTextView.setText("Tổng cộng: " + decimalFormat.format(totalPrice));
    }

    /**
     * Tính toán tổng giá trị của giỏ hàng.
     */
    private int calculateTotalPrice() {
        int totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    /**
     * Tạo chi tiết sản phẩm trong giỏ hàng.
     */
    private String getProductDetails() {
        StringBuilder productDetails = new StringBuilder();
        for (CartItem item : cartItems) {
            productDetails.append(item.getProductName())
                    .append(" (x").append(item.getQuantity()).append(") - ")
                    .append(item.getPrice()).append("đ\n");
        }
        return productDetails.toString();
    }

    /**
     * Xử lý khi nhấn nút Đặt Hàng.
     */
    private void handleOrderPlacement() {
        // Kiểm tra xem giỏ hàng có sản phẩm không
        if (cartItems.isEmpty()) {
            Toast.makeText(CartActivity.this, "Giỏ hàng của bạn đang trống", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra nếu không có địa chỉ nhận hàng
//        if (address == null || address.isEmpty()) {
//            Toast.makeText(CartActivity.this, "Vui lòng thêm địa chỉ nhận hàng", Toast.LENGTH_SHORT).show();
//            return;
//        }

        // Thông tin đơn hàng
        String productDetails = getProductDetails();
        int totalPrice = calculateTotalPrice();
        String orderStatus = "Chờ phê duyệt";  // Trạng thái đơn hàng

        // Tạo đối tượng Order và thêm vào cơ sở dữ liệu
        Order order = new Order(address, totalPrice, orderStatus, cartItems);
        cartDAO.addOrder(order); // Lưu đơn hàng vào cơ sở dữ liệu

        // Xóa giỏ hàng sau khi đặt hàng
        cartDAO.clearCart();
        cartItems.clear();
        cartAdapter.notifyDataSetChanged();

        // Thông báo và chuyển sang màn hình xác nhận đơn hàng
        Toast.makeText(CartActivity.this, "Đơn hàng đang chờ phê duyệt", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CartActivity.this, OrderConfirmationActivity.class);
        intent.putExtra("address", address);
        intent.putExtra("productDetails", productDetails);
        intent.putExtra("totalPrice", totalPrice);
        intent.putExtra("orderStatus", orderStatus);
        startActivity(intent);

        // Kết thúc Activity hiện tại
        finish();
    }
    // Phương thức lấy orderId của đơn hàng vừa thêm vào cơ sở dữ liệu
    private int getLatestOrderId() {
        // Lấy orderId mới nhất từ cơ sở dữ liệu

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(CartDatabaseHelper.TABLE_ORDERS, new String[]{"MAX(order_id) AS max_order_id"}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int orderId = cursor.getInt(cursor.getColumnIndex("max_order_id"));
            cursor.close();
            db.close();
            return orderId;
        }
        db.close();
        return -1; // Trả về -1 nếu không có đơn hàng
    }
}
