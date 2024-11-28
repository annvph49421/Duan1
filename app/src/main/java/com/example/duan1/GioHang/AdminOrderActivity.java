package com.example.duan1.GioHang;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1.R;
import com.example.duan1.SQLite.CartDatabaseHelper;

public class AdminOrderActivity extends AppCompatActivity {
    private TextView addressTextView, productsTextView, totalPriceTextView, orderStatusTextView;
    private Button btnConfirmOrder;
    private CartDatabaseHelper dbHelper;

    private int orderId; // ID của đơn hàng để quản lý trạng thái

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);

        // Ánh xạ giao diện
        addressTextView = findViewById(R.id.addressTextView);
        productsTextView = findViewById(R.id.productsTextView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        orderStatusTextView = findViewById(R.id.orderStatusTextView);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);

        // Khởi tạo SQLiteHelper
        dbHelper = new CartDatabaseHelper(this);

        // Lấy ID đơn hàng từ Intent
        orderId = getIntent().getIntExtra("orderId", -1);

        if (orderId != -1) {
            loadOrderDetails(orderId);
        } else {
            Toast.makeText(this, "Không tìm thấy đơn hàng", Toast.LENGTH_SHORT).show();
        }

        // Xử lý sự kiện xác nhận đơn hàng
        btnConfirmOrder.setOnClickListener(v -> confirmOrder());
    }

    private void loadOrderDetails(int orderId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn thông tin đơn hàng từ database
        Cursor cursor = db.query("orders", null, "id = ?", new String[]{String.valueOf(orderId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String productDetails = cursor.getString(cursor.getColumnIndex("product_details"));
            int totalPrice = cursor.getInt(cursor.getColumnIndex("total_price"));
            String orderStatus = cursor.getString(cursor.getColumnIndex("status"));

            // Cập nhật giao diện
            addressTextView.setText("Địa chỉ: " + address);
            productsTextView.setText("Sản phẩm: " + productDetails);
            totalPriceTextView.setText("Tổng cộng: " + totalPrice + "đ");
            orderStatusTextView.setText("Trạng thái: " + orderStatus);

            cursor.close();
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin đơn hàng", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmOrder() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Cập nhật trạng thái đơn hàng
        ContentValues values = new ContentValues();
        values.put("status", "Đã xác nhận");

        int rowsUpdated = db.update("orders", values, "id = ?", new String[]{String.valueOf(orderId)});

        if (rowsUpdated > 0) {
            orderStatusTextView.setText("Trạng thái: Đã xác nhận");
            Toast.makeText(this, "Đơn hàng đã được xác nhận", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lỗi khi xác nhận đơn hàng", Toast.LENGTH_SHORT).show();
        }
    }
}