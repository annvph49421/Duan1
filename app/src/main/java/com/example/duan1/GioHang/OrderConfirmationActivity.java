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





public class OrderConfirmationActivity extends AppCompatActivity {
    private TextView tvAddress, tvProducts, tvTotalPrice, tvOrderStatus;
    private CartDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        // Ánh xạ view
        tvAddress = findViewById(R.id.tvAddress);
        tvProducts = findViewById(R.id.tvProducts);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);

        // Kết nối Database
        dbHelper = new CartDatabaseHelper(this);

        // Lấy thông tin đơn hàng (thay 1 bằng ID đơn hàng cụ thể)
        loadOrderDetails(1);
    }

    private void loadOrderDetails(int orderId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn bảng orders
        Cursor orderCursor = db.rawQuery("SELECT * FROM " + CartDatabaseHelper.TABLE_ORDERS +
                        " WHERE " + CartDatabaseHelper.COLUMN_ORDER_ID + " = ?",
                new String[]{String.valueOf(orderId)});
        if (orderCursor.moveToFirst()) {
            String address = orderCursor.getString(orderCursor.getColumnIndex(CartDatabaseHelper.COLUMN_ORDER_ADDRESS));
            int totalPrice = orderCursor.getInt(orderCursor.getColumnIndex(CartDatabaseHelper.COLUMN_ORDER_TOTAL_PRICE));
            String status = orderCursor.getString(orderCursor.getColumnIndex(CartDatabaseHelper.COLUMN_ORDER_STATUS));

            // Gán dữ liệu vào giao diện
            tvAddress.setText("Địa chỉ: " + address);
            tvTotalPrice.setText("Tổng cộng: " + totalPrice + "đ");
            tvOrderStatus.setText("Trạng thái: " + status);
        }
        orderCursor.close();

        // Truy vấn bảng cart để lấy danh sách sản phẩm
        Cursor productCursor = db.rawQuery("SELECT * FROM " + CartDatabaseHelper.TABLE_CART, null);
        StringBuilder productDetails = new StringBuilder();
        while (productCursor.moveToNext()) {
            String productName = productCursor.getString(productCursor.getColumnIndex(CartDatabaseHelper.COLUMN_PRODUCT_NAME));
            int quantity = productCursor.getInt(productCursor.getColumnIndex(CartDatabaseHelper.COLUMN_QUANTITY));
            int price = productCursor.getInt(productCursor.getColumnIndex(CartDatabaseHelper.COLUMN_PRICE));
            productDetails.append(productName)
                    .append(" x")
                    .append(quantity)
                    .append(" - ")
                    .append(price * quantity)
                    .append("đ\n");
        }
        productCursor.close();

        tvProducts.setText("Sản phẩm:\n" + productDetails.toString());
    }
    private void placeOrder() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

    }

}