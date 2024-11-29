package com.example.duan1.GioHang;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;
import com.example.duan1.SQLite.CartDatabaseHelper;

public class OrderConfirmationActivity extends AppCompatActivity {

    private TextView tvProductDetails, tvOrderAddress, tvTotalPrice, tvOrderStatus;
    private Button btnConfirmOrder, btnCancelOrder;
    private CartDatabaseHelper dbHelper;
    private String address, productDetails;
    private int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        // Ánh xạ các view
        tvProductDetails = findViewById(R.id.tvProductDetails);
        tvOrderAddress = findViewById(R.id.tvOrderAddress);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);
        btnCancelOrder = findViewById(R.id.btnCancelOrder);

        dbHelper = new CartDatabaseHelper(this);

        // Nhận dữ liệu từ CartActivity
        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        productDetails = intent.getStringExtra("productDetails");
        totalPrice = intent.getIntExtra("totalPrice", 0);

        // Kiểm tra dữ liệu hợp lệ
        if (address == null || productDetails == null || totalPrice == 0) {
            Toast.makeText(this, "Dữ liệu đơn hàng không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hiển thị thông tin đơn hàng
        tvProductDetails.setText(productDetails);
        tvOrderAddress.setText(address);
        tvTotalPrice.setText("Tổng cộng: " + totalPrice + "đ");
        tvOrderStatus.setText("Trạng thái: Chờ xác nhận");

        // Xử lý khi nhấn nút Xác nhận
        btnConfirmOrder.setOnClickListener(v -> {
            long orderId = saveOrder();
            if (orderId != -1) {
                Toast.makeText(this, "Đơn hàng đã được gửi!", Toast.LENGTH_SHORT).show();

                // Gửi thông tin đến Admin
                Intent adminIntent = new Intent(this, AdminOrderActivity.class);
                adminIntent.putExtra("orderId", (int) orderId);
                startActivity(adminIntent);
                finish();
            } else {
                Toast.makeText(this, "Lỗi khi gửi đơn hàng!", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý khi nhấn nút Hủy
        btnCancelOrder.setOnClickListener(v -> finish());
    }

    // Lưu đơn hàng vào cơ sở dữ liệu
    private long saveOrder() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CartDatabaseHelper.COLUMN_ORDER_ADDRESS, address);
        values.put(CartDatabaseHelper.COLUMN_ORDER_PRODUCT_DETAILS, productDetails);
        values.put(CartDatabaseHelper.COLUMN_ORDER_TOTAL_PRICE, totalPrice);
        values.put(CartDatabaseHelper.COLUMN_ORDER_STATUS, "Chờ xác nhận");

        long orderId = db.insert(CartDatabaseHelper.TABLE_ORDERS, null, values);
        if (orderId == -1) {
            Toast.makeText(this, "Lỗi khi lưu đơn hàng!", Toast.LENGTH_SHORT).show();
        }
        return orderId;
    }
    private void loadOrderFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(CartDatabaseHelper.TABLE_ORDERS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String address = cursor.getString(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_ORDER_ADDRESS));
                String productDetails = cursor.getString(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_ORDER_PRODUCT_DETAILS));
                int totalPrice = cursor.getInt(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_ORDER_TOTAL_PRICE));

                // Hiển thị thông tin đơn hàng
                tvOrderAddress.setText(address);
                tvProductDetails.setText(productDetails);
                tvTotalPrice.setText("Tổng cộng: " + totalPrice + "đ");
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

}
