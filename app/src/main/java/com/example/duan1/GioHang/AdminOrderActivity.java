package com.example.duan1.GioHang;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;
import com.example.duan1.SQLite.CartDatabaseHelper;

public class AdminOrderActivity extends AppCompatActivity {

    private TextView addressTextView, productDetailsTextView, totalPriceTextView, orderStatusTextView;
    private int orderId;
    private CartDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);

        // Ánh xạ các view
        addressTextView = findViewById(R.id.addressTextView);
        productDetailsTextView = findViewById(R.id.productDetailsTextView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        orderStatusTextView = findViewById(R.id.orderStatusTextView);

        dbHelper = new CartDatabaseHelper(this);

        // Nhận orderId từ Intent
        orderId = getIntent().getIntExtra("orderId", -1);
        if (orderId != -1) {
            loadOrderDetails(orderId);
        } else {
            Toast.makeText(this, "Không tìm thấy đơn hàng", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadOrderDetails(int orderId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(CartDatabaseHelper.TABLE_ORDERS, null, "order_id = ?", new String[]{String.valueOf(orderId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String address = cursor.getString(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_ORDER_ADDRESS));
            String productDetails = cursor.getString(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_ORDER_PRODUCT_DETAILS));
            int totalPrice = cursor.getInt(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_ORDER_TOTAL_PRICE));
            String orderStatus = cursor.getString(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_ORDER_STATUS));

            addressTextView.setText(address);
            productDetailsTextView.setText(productDetails);
            totalPriceTextView.setText("Tổng cộng: " + totalPrice + "đ");
            orderStatusTextView.setText("Trạng thái: " + orderStatus);

            cursor.close();
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin đơn hàng", Toast.LENGTH_SHORT).show();
        }
    }
}
