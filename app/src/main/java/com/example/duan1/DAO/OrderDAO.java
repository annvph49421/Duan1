package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Models.CartItem;
import com.example.duan1.Models.Order;
import com.example.duan1.SQLite.DatabaseHelper;

public class OrderDAO {
    private SQLiteDatabase db;

    public OrderDAO(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void addOrder(Order order) {
        ContentValues values = new ContentValues();
        values.put("address", order.getAddress());
        values.put("totalPrice", order.getTotalPrice());
        values.put("status", order.getStatus());

        long orderId = db.insert("orders", null, values); // Lưu đơn hàng

        for (CartItem item : order.getCartItems()) {
            ContentValues itemValues = new ContentValues();
            itemValues.put("orderId", orderId);
            itemValues.put("productName", item.getProductName());
            itemValues.put("quantity", item.getQuantity());
            itemValues.put("price", item.getPrice());
            db.insert("order_items", null, itemValues); // Lưu sản phẩm trong đơn hàng
        }
    }
}

