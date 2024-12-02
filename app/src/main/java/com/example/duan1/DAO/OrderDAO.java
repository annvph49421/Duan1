package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Models.CartItem;
import com.example.duan1.Models.Order;
import com.example.duan1.SQLite.OrderDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private SQLiteDatabase database;
    private OrderDatabaseHelper dbHelper;

    public OrderDAO(Context context) {
        dbHelper = new OrderDatabaseHelper(context);
    }

    // Thêm đơn hàng vào cơ sở dữ liệu
    public long addOrder(Order order) {
        SQLiteDatabase db = null;
        long orderId = -1;

        try {
            // Mở cơ sở dữ liệu
            db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(OrderDatabaseHelper.COLUMN_ADDRESS, order.getAddress());
            values.put(OrderDatabaseHelper.COLUMN_STATUS, order.getStatus());
            values.put(OrderDatabaseHelper.COLUMN_PRODUCT_DETAILS, order.getProductDetails());

            // Kiểm tra nếu cartItems là null, nếu có thì khởi tạo danh sách trống
            List<CartItem> cartItems = order.getCartItems();
            if (cartItems == null) {
                cartItems = new ArrayList<>();  // Khởi tạo danh sách trống nếu là null
            }

            // Tính tổng giá cho đơn hàng từ các sản phẩm (nếu cần)
            int totalPrice = 0;
            for (CartItem item : cartItems) {
                totalPrice += item.getPrice() * item.getQuantity();  // Tính tổng giá cho sản phẩm
            }

            // Lưu tổng giá vào cơ sở dữ liệu
            values.put(OrderDatabaseHelper.COLUMN_TOTAL_PRICE, totalPrice);

            // Thêm đơn hàng vào cơ sở dữ liệu
            orderId = db.insert(OrderDatabaseHelper.TABLE_ORDERS, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo rằng cơ sở dữ liệu được đóng khi không sử dụng nữa
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return orderId;
    }



    // Lấy tất cả đơn hàng từ cơ sở dữ liệu
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            // Mở cơ sở dữ liệu ở chế độ đọc
            db = dbHelper.getReadableDatabase();

            // Truy vấn tất cả các đơn hàng
            cursor = db.query(OrderDatabaseHelper.TABLE_ORDERS, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Lấy dữ liệu từ cursor và tạo đối tượng Order
                    int orderId = cursor.getInt(cursor.getColumnIndex(OrderDatabaseHelper.COLUMN_ORDER_ID));
                    String address = cursor.getString(cursor.getColumnIndex(OrderDatabaseHelper.COLUMN_ADDRESS));
                    int totalPrice = cursor.getInt(cursor.getColumnIndex(OrderDatabaseHelper.COLUMN_TOTAL_PRICE));
                    String status = cursor.getString(cursor.getColumnIndex(OrderDatabaseHelper.COLUMN_STATUS));
                    String productDetails = cursor.getString(cursor.getColumnIndex(OrderDatabaseHelper.COLUMN_PRODUCT_DETAILS));

                    // Tạo đối tượng Order và thêm vào danh sách
                    Order order = new Order(address, totalPrice, status, productDetails, orderId);
                    orders.add(order);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi nếu có
        } finally {
            // Đảm bảo đóng cursor và cơ sở dữ liệu khi không sử dụng nữa
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return orders;
    }

    // Lấy đơn hàng theo ID
    public Order getOrderById(int orderId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Order order = null;

        try {
            db = dbHelper.getReadableDatabase();

            // Truy vấn đơn hàng theo ID
            String selection = OrderDatabaseHelper.COLUMN_ORDER_ID + " = ?";
            String[] selectionArgs = {String.valueOf(orderId)};
            cursor = db.query(OrderDatabaseHelper.TABLE_ORDERS, null, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String address = cursor.getString(cursor.getColumnIndex(OrderDatabaseHelper.COLUMN_ADDRESS));
                int totalPrice = cursor.getInt(cursor.getColumnIndex(OrderDatabaseHelper.COLUMN_TOTAL_PRICE));
                String status = cursor.getString(cursor.getColumnIndex(OrderDatabaseHelper.COLUMN_STATUS));
                String productDetails = cursor.getString(cursor.getColumnIndex(OrderDatabaseHelper.COLUMN_PRODUCT_DETAILS));

                order = new Order(address, totalPrice, status, productDetails, orderId);
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi nếu có
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return order;
    }

    // Cập nhật đơn hàng trong cơ sở dữ liệu
    public int updateOrder(Order order) {
        SQLiteDatabase db = null;
        int rowsAffected = 0;

        try {
            db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(OrderDatabaseHelper.COLUMN_ADDRESS, order.getAddress());
            values.put(OrderDatabaseHelper.COLUMN_STATUS, order.getStatus());
            values.put(OrderDatabaseHelper.COLUMN_TOTAL_PRICE, order.getTotalPrice());
            values.put(OrderDatabaseHelper.COLUMN_PRODUCT_DETAILS, order.getProductDetails());

            // Cập nhật đơn hàng theo ID
            String whereClause = OrderDatabaseHelper.COLUMN_ORDER_ID + " = ?";
            String[] whereArgs = {String.valueOf(order.getOrderId())};

            rowsAffected = db.update(OrderDatabaseHelper.TABLE_ORDERS, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi nếu có
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return rowsAffected;
    }

    // Xóa đơn hàng trong cơ sở dữ liệu
    public int deleteOrder(int orderId) {
        SQLiteDatabase db = null;
        int rowsAffected = 0;

        try {
            db = dbHelper.getWritableDatabase();

            // Xóa đơn hàng theo ID
            String whereClause = OrderDatabaseHelper.COLUMN_ORDER_ID + " = ?";
            String[] whereArgs = {String.valueOf(orderId)};

            rowsAffected = db.delete(OrderDatabaseHelper.TABLE_ORDERS, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi nếu có
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return rowsAffected;
    }

    public void updateOrderStatus(long orderId, String approvalStatus) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OrderDatabaseHelper.COLUMN_APPROVAL_STATUS, approvalStatus);  // Cập nhật trạng thái phê duyệt

        String selection = OrderDatabaseHelper.COLUMN_ORDER_ID + " = ?";
        String[] selectionArgs = { String.valueOf(orderId) };

        db.update(OrderDatabaseHelper.TABLE_ORDERS, values, selection, selectionArgs);
        db.close();
    }

}
