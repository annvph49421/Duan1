package com.example.duan1.GioHang;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;
import com.example.duan1.SQLite.CartDatabaseHelper;

public class OrderConfirmationActivity extends AppCompatActivity {

    private CartDatabaseHelper dbHelper;
    private String address;
    private String productDetails;
    private int totalPrice;

    // Các view trong layout
    private TextView tvOrderAddress, tvProductDetails, tvTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        dbHelper = new CartDatabaseHelper(this);

        // Giả sử bạn đã lấy dữ liệu đơn hàng từ UI hoặc từ giỏ hàng
        address = "123 Đường ABC";
        productDetails = "Iphone 15 Pro Max 256GB (x1) - 31000000đ";
        totalPrice = 31000000;

        // Gán dữ liệu vào các TextView để hiển thị
        tvOrderAddress = findViewById(R.id.tvOrderAddress);
        tvProductDetails = findViewById(R.id.tvProductDetails);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        tvOrderAddress.setText(address);
        tvProductDetails.setText(productDetails);
        tvTotalPrice.setText("Tổng cộng: " + totalPrice + "đ");

        // Lắng nghe sự kiện nút "Đặt hàng"
        Button btnPlaceOrder = findViewById(R.id.btnConfirmOrder);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức saveOrder để lưu đơn hàng
                long orderId = saveOrder();

                if (orderId != -1) {
                    Toast.makeText(OrderConfirmationActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                    // Tiến hành các bước tiếp theo, ví dụ: chuyển sang màn hình xác nhận, thanh toán...
                } else {
                    Toast.makeText(OrderConfirmationActivity.this, "Lỗi khi đặt hàng!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Phương thức lưu đơn hàng vào cơ sở dữ liệu
    private long saveOrder() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CartDatabaseHelper.COLUMN_ORDER_ADDRESS, address);
        values.put(CartDatabaseHelper.COLUMN_ORDER_PRODUCT_DETAILS, productDetails);
        values.put(CartDatabaseHelper.COLUMN_ORDER_TOTAL_PRICE, totalPrice);
        values.put(CartDatabaseHelper.COLUMN_ORDER_STATUS, "Chờ xác nhận");

        // Đảm bảo commit transaction
        db.beginTransaction();
        try {
            long result = db.insert(CartDatabaseHelper.TABLE_ORDERS, null, values);

            if (result == -1) {
                Log.e("SaveOrder", "Lỗi khi lưu đơn hàng vào cơ sở dữ liệu");
                db.endTransaction();
                return -1;
            }

            // Commit transaction nếu thành công
            db.setTransactionSuccessful();
            Log.d("SaveOrder", "Đơn hàng đã được lưu với ID: " + result);
            return result;
        } finally {
            db.endTransaction(); // Đảm bảo kết thúc transaction
        }
    }
}
