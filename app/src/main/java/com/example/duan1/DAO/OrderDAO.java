
package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1.Models.CartItem;
import com.example.duan1.Models.Order;
import com.example.duan1.SQLite.OrderDatabaseHelper;
import com.example.duan1.SQLite.TopPhone;

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

            // Truy vấn tất cả các đơn hàng và sắp xếp theo orderId giảm dần (đơn hàng mới lên đầu)
            cursor = db.query(OrderDatabaseHelper.TABLE_ORDERS, null, null, null, null, null,
                    OrderDatabaseHelper.COLUMN_ORDER_ID + " DESC");

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Lấy dữ liệu từ cursor và tạo đối tượng Order
                    int orderId = cursor.getInt(cursor.getColumnIndex(OrderDatabaseHelper.COLUMN_ORDER_ID));
                    String address = cursor.getString(cursor.getColumnIndex(OrderDatabaseHelper.COLUMN_ADDRESS));
                    int totalPrice = cursor.getInt(cursor.getColumnIndex(OrderDatabaseHelper.COLUMN_TOTAL_PRICE));
                    String status = cursor.getString(cursor.getColumnIndex(OrderDatabaseHelper.COLUMN_STATUS));
                    String productDetails = cursor.getString(cursor.getColumnIndex(OrderDatabaseHelper.COLUMN_PRODUCT_DETAILS));
                    String approvalStatus = cursor.getString(cursor.getColumnIndex(OrderDatabaseHelper.COLUMN_APPROVAL_STATUS)); // Lấy trạng thái phê duyệt

                    // Tạo đối tượng Order và thêm vào danh sách
                    Order order = new Order(address, totalPrice, status, productDetails, orderId);
                    order.setApprovalStatus(approvalStatus); // Gán trạng thái phê duyệt
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
    public Order getOrderId(int orderId) {
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
            // Mở cơ sở dữ liệu ở chế độ ghi
            db = dbHelper.getWritableDatabase();

            // Log ID cần xóa
            Log.d("OrderDAO", "Attempting to delete order with ID: " + orderId);

            // Kiểm tra nếu ID hợp lệ
            if (orderId <= 0) {
                Log.e("OrderDAO", "Invalid orderId: " + orderId);
                return 0; // Trả về 0 nếu orderId không hợp lệ
            }

            // Xóa đơn hàng với ID cụ thể
            String whereClause = OrderDatabaseHelper.COLUMN_ORDER_ID + " = ?";
            String[] whereArgs = {String.valueOf(orderId)};

            // Thực hiện lệnh xóa
            rowsAffected = db.delete(OrderDatabaseHelper.TABLE_ORDERS, whereClause, whereArgs);

            // Log kết quả xóa
            Log.d("OrderDAO", "Rows affected: " + rowsAffected);

        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi nếu có
        } finally {
            // Đảm bảo đóng cơ sở dữ liệu khi không sử dụng
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return rowsAffected; // Trả về số dòng bị ảnh hưởng
    }



    public int updateOrderStatus(int orderId, String newStatus) {
        SQLiteDatabase db = null;
        int rowsAffected = 0;

        try {
            // Mở cơ sở dữ liệu ở chế độ ghi
            db = dbHelper.getWritableDatabase();

            // Tạo ContentValues để cập nhật giá trị trạng thái
            ContentValues values = new ContentValues();
            values.put(OrderDatabaseHelper.COLUMN_STATUS, newStatus);  // Cập nhật trạng thái đơn hàng
            values.put(OrderDatabaseHelper.COLUMN_APPROVAL_STATUS,newStatus);  // Cập nhật trạng thái phê duyệt

            // Chỉ định điều kiện để tìm đơn hàng cần cập nhật
            String selection = OrderDatabaseHelper.COLUMN_ORDER_ID + " = ?";
            String[] selectionArgs = { String.valueOf(orderId) };

            // Thực hiện truy vấn update
            rowsAffected = db.update(OrderDatabaseHelper.TABLE_ORDERS, values, selection, selectionArgs);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo rằng cơ sở dữ liệu được đóng khi không sử dụng nữa
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return rowsAffected;  // Trả về số dòng bị ảnh hưởng
    }



    // OrderDAO.java
    public List<TopPhone> getTopPhonesByPeriod(String period) {
        SQLiteDatabase db = null;
        List<TopPhone> topPhones = new ArrayList<>();
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            String query = "SELECT product_details, SUM(quantity) as sold_quantity FROM orders GROUP BY product_details ORDER BY sold_quantity DESC";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String productName = cursor.getString(cursor.getColumnIndex("product_details"));
                    int soldQuantity = cursor.getInt(cursor.getColumnIndex("sold_quantity"));
                    TopPhone topPhone = new TopPhone(productName, soldQuantity);
                    topPhones.add(topPhone);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return topPhones;
    }





}
