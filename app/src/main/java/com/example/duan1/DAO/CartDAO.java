package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
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

        // Kiểm tra xem địa chỉ đã tồn tại chưa
        Cursor cursor = db.query(CartDatabaseHelper.TABLE_ADDRESS, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            // Cập nhật địa chỉ
            db.update(CartDatabaseHelper.TABLE_ADDRESS, values, null, null);
        } else {
            // Thêm địa chỉ mới
            db.insert(CartDatabaseHelper.TABLE_ADDRESS, null, values);
        }
        if (cursor != null) cursor.close();
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
            if (cursor != null) cursor.close();
            db.close();
            return null; // Không có địa chỉ
        }
    }



}