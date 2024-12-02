package com.example.duan1.SQLite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "order_db";
    private static final int DATABASE_VERSION = 3;  // Tăng version khi thay đổi cấu trúc

    // Tên bảng và cột
    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_TOTAL_PRICE = "total_price";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_PRODUCT_DETAILS = "product_details";
    public static final String COLUMN_APPROVAL_STATUS = "approval_status";  // Thêm cột trạng thái phê duyệt

    public OrderDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ADDRESS + " TEXT,"
                + COLUMN_TOTAL_PRICE + " INTEGER,"
                + COLUMN_STATUS + " TEXT,"
                + COLUMN_PRODUCT_DETAILS + " TEXT,"
                + COLUMN_APPROVAL_STATUS + " TEXT DEFAULT 'Pending')";
        db.execSQL(CREATE_ORDERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            String ADD_APPROVAL_STATUS_COLUMN = "ALTER TABLE " + TABLE_ORDERS + " ADD COLUMN " + COLUMN_APPROVAL_STATUS + " TEXT DEFAULT 'Pending'";
            db.execSQL(ADD_APPROVAL_STATUS_COLUMN);
        }
    }
}
