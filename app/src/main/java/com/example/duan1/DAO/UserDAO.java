package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.SQLite.CartDatabaseHelper;

public class UserDAO {
    private CartDatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new CartDatabaseHelper(context);
    }

    // Lấy vai trò người dùng
    public String getUserRole(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(CartDatabaseHelper.TABLE_USERS,
                new String[]{CartDatabaseHelper.COLUMN_ROLE},
                CartDatabaseHelper.COLUMN_USERNAME + " = ?",
                new String[]{username}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String role = cursor.getString(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_ROLE));
            cursor.close();
            return role;
        }
        return null; // Không tìm thấy vai trò
    }

    // Thêm người dùng mới
    public void addUser(String username, String password, String role) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CartDatabaseHelper.COLUMN_USERNAME, username);
        values.put(CartDatabaseHelper.COLUMN_PASSWORD, password); // Mã hóa mật khẩu trong thực tế
        values.put(CartDatabaseHelper.COLUMN_ROLE, role);

        db.insert(CartDatabaseHelper.TABLE_USERS, null, values);
    }
}
