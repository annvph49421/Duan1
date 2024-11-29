package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Models.CartItem;
import com.example.duan1.Models.Order;
import com.example.duan1.SQLite.CartDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    private CartDatabaseHelper dbHelper;

    public CartDAO(Context context) {
        dbHelper = new CartDatabaseHelper(context);
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addToCart(CartItem item) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CartDatabaseHelper.COLUMN_PRODUCT_NAME, item.getProductName());
            values.put(CartDatabaseHelper.COLUMN_QUANTITY, item.getQuantity());
            values.put(CartDatabaseHelper.COLUMN_PRICE, item.getPrice());
            values.put(CartDatabaseHelper.COLUMN_IMAGE, item.getImageResId());
            db.insert(CartDatabaseHelper.TABLE_CART, null, values);
        } catch (Exception e) {
            // Xử lý lỗi nếu có
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    // Lấy danh sách sản phẩm trong giỏ hàng
    public List<CartItem> getCartItems() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<CartItem> cartItems = new ArrayList<>();

        Cursor cursor = db.query(CartDatabaseHelper.TABLE_CART, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String productName = cursor.getString(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_PRODUCT_NAME));
                int quantity = cursor.getInt(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_QUANTITY));
                int price = cursor.getInt(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_PRICE));
                int image = cursor.getInt(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_IMAGE));

                cartItems.add(new CartItem(productName, quantity, price, image));
            }
            cursor.close();
        }
        db.close();
        return cartItems;
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    public void updateQuantity(CartItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CartDatabaseHelper.COLUMN_QUANTITY, item.getQuantity());

        db.update(CartDatabaseHelper.TABLE_CART, values, CartDatabaseHelper.COLUMN_PRODUCT_NAME + " = ?", new String[]{item.getProductName()});
        db.close();
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeItem(CartItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(CartDatabaseHelper.TABLE_CART, CartDatabaseHelper.COLUMN_PRODUCT_NAME + " = ?", new String[]{item.getProductName()});
        db.close();
    }

    // Xóa tất cả sản phẩm trong giỏ hàng
    public void clearCart() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(CartDatabaseHelper.TABLE_CART, null, null);
        db.close();
    }

    // Thêm địa chỉ vào cơ sở dữ liệu
    public void addAddress(String address) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CartDatabaseHelper.COLUMN_ADDRESS, address);

        // Kiểm tra nếu đã có địa chỉ, nếu có thì cập nhật, nếu không thì thêm mới
        Cursor cursor = db.query(CartDatabaseHelper.TABLE_ADDRESS, null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            // Cập nhật địa chỉ nếu đã có
            db.update(CartDatabaseHelper.TABLE_ADDRESS, values, null, null);
        } else {
            // Thêm mới địa chỉ nếu chưa có
            db.insert(CartDatabaseHelper.TABLE_ADDRESS, null, values);
        }
        cursor.close();
        db.close();
    }

    // Lấy địa chỉ từ cơ sở dữ liệu
    public String getAddress() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(CartDatabaseHelper.TABLE_ADDRESS, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String address = cursor.getString(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_ADDRESS));
            cursor.close();
            db.close();
            return address;
        } else {
            db.close();
            return null;
        }
    }

    // Thêm đơn hàng vào cơ sở dữ liệu
    public void addOrder(Order order) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CartDatabaseHelper.COLUMN_ORDER_ADDRESS, order.getAddress());
        values.put(CartDatabaseHelper.COLUMN_ORDER_TOTAL_PRICE, order.getTotalPrice());
        values.put(CartDatabaseHelper.COLUMN_ORDER_STATUS, order.getStatus());
        values.put(CartDatabaseHelper.COLUMN_ORDER_PRODUCT_DETAILS, order.getProductDetails());

        long orderId = db.insert(CartDatabaseHelper.TABLE_ORDERS, null, values);
        db.close();

        // Cập nhật lại ID cho đơn hàng vừa thêm
        order.setOrderId((int) orderId);
    }

    // Lấy thông tin đơn hàng từ cơ sở dữ liệu
    public Order getOrder(int orderId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(CartDatabaseHelper.TABLE_ORDERS, null, "order_id = ?", new String[]{String.valueOf(orderId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String address = cursor.getString(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_ORDER_ADDRESS));
            int totalPrice = cursor.getInt(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_ORDER_TOTAL_PRICE));
            String status = cursor.getString(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_ORDER_STATUS));
            String productDetails = cursor.getString(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_ORDER_PRODUCT_DETAILS));

            cursor.close();
            db.close();

            // Tạo đối tượng Order
            Order order = new Order(address, totalPrice, status, null);
            order.setOrderId(orderId);
            order.setProductDetails(productDetails);
            return order;
        }

        db.close();
        return null;
    }

    // Cập nhật trạng thái đơn hàng (Xác nhận hoặc hủy)
    public boolean updateOrderStatus(int orderId, String status) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CartDatabaseHelper.COLUMN_ORDER_STATUS, status);

        // Cập nhật đơn hàng theo orderId
        int rowsAffected = db.update(CartDatabaseHelper.TABLE_ORDERS, values, CartDatabaseHelper.COLUMN_ORDER_ID + " = ?", new String[]{String.valueOf(orderId)});
        db.close();
        return rowsAffected > 0; // Nếu cập nhật thành công, trả về true
    }
    // Method to get the orderId by address
    public int getOrderIdByAddress(String address) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(CartDatabaseHelper.TABLE_ORDERS,
                new String[]{CartDatabaseHelper.COLUMN_ORDER_ID},
                CartDatabaseHelper.COLUMN_ORDER_ADDRESS + " = ?",
                new String[]{address},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int orderId = cursor.getInt(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_ORDER_ID));
            cursor.close();
            return orderId;
        }
        cursor.close();
        return -1;  // Indicating no order found
    }


}
