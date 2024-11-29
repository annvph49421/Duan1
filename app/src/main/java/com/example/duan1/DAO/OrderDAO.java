package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1.Models.CartItem;
import com.example.duan1.Models.Order;
import com.example.duan1.SQLite.DatabaseHelper;

public class OrderDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public OrderDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Phương thức thêm đơn hàng vào cơ sở dữ liệu
    public void addOrder(Order order) {
        // Bắt đầu giao dịch
        db.beginTransaction();
        try {
            // Thêm thông tin đơn hàng vào bảng orders
            ContentValues values = new ContentValues();
            values.put("address", order.getAddress());
            values.put("totalPrice", order.getTotalPrice());
            values.put("status", order.getStatus());

            // Thêm đơn hàng vào bảng orders
            long orderId = db.insert("orders", null, values);

            if (orderId != -1) {
                // Nếu đơn hàng được thêm thành công, thêm sản phẩm trong đơn hàng
                for (CartItem item : order.getCartItems()) {
                    ContentValues itemValues = new ContentValues();
                    itemValues.put("orderId", orderId);  // Liên kết sản phẩm với đơn hàng
                    itemValues.put("productName", item.getProductName());
                    itemValues.put("quantity", item.getQuantity());
                    itemValues.put("price", item.getPrice());

                    // Thêm sản phẩm vào bảng order_items
                    db.insert("order_items", null, itemValues);
                }

                // Đánh dấu giao dịch thành công
                db.setTransactionSuccessful();
            } else {
                Log.e("OrderDAO", "Không thể thêm đơn hàng vào cơ sở dữ liệu");
            }
        } catch (Exception e) {
            Log.e("OrderDAO", "Lỗi khi thêm đơn hàng: " + e.getMessage());
        } finally {
            // Kết thúc giao dịch
            db.endTransaction();
        }
    }

    // Đảm bảo đóng database khi không còn sử dụng
    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    // Phương thức cập nhật trạng thái đơn hàng
    public void updateOrderStatus(int orderId, String newStatus) {
        ContentValues values = new ContentValues();
        values.put("status", newStatus);

        try {
            // Cập nhật trạng thái của đơn hàng trong bảng orders
            int rowsUpdated = db.update("orders", values, "order_id = ?", new String[]{String.valueOf(orderId)});

            if (rowsUpdated > 0) {
                Log.d("OrderDAO", "Cập nhật trạng thái đơn hàng thành công.");
            } else {
                Log.e("OrderDAO", "Không tìm thấy đơn hàng để cập nhật.");
            }
        } catch (Exception e) {
            Log.e("OrderDAO", "Lỗi khi cập nhật trạng thái đơn hàng: " + e.getMessage());
        }
    }

//    // Đảm bảo đóng database khi không còn sử dụng
//    public void aVoid() {
//        if (db != null && db.isOpen()) {
//            db.close();
//        }
//    }
}
