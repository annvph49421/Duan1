package com.example.duan1.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CartDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CartDatabase.db";
    private static final int DATABASE_VERSION = 16; // Cập nhật version

    // Bảng giỏ hàng
    public static final String TABLE_CART = "cart";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_IMAGE = "image";

//    // Bảng đơn hàng
//    public static final String TABLE_ORDERS = "orders";
//    public static final String COLUMN_ORDER_ID = "order_id";
//    public static final String COLUMN_ORDER_ADDRESS = "address";
//    public static final String COLUMN_ORDER_PRODUCT_DETAILS = "product_details";
//    public static final String COLUMN_ORDER_TOTAL_PRICE = "total_price";
//    public static final String COLUMN_ORDER_STATUS = "status";
//
//    // Bảng chi tiết đơn hàng
//    public static final String TABLE_ORDER_ITEMS = "order_items";
//    public static final String COLUMN_ITEM_ID = "item_id";
//    public static final String COLUMN_ITEM_ORDER_ID = "order_id"; // Liên kết với bảng orders
//    public static final String COLUMN_ITEM_PRODUCT_NAME = "product_name";
//    public static final String COLUMN_ITEM_QUANTITY = "quantity";
//    public static final String COLUMN_ITEM_PRICE = "price";
    // dia chi
    public static final String TABLE_ADDRESS = "address";
    public static final String COLUMN_ADDRESS_ID = "id";
    public static final String COLUMN_ADDRESS = "address";

//    // Bảng người dùng
//    public static final String TABLE_USERS = "users";
//    public static final String COLUMN_USER_ID = "user_id";
//    public static final String COLUMN_USERNAME = "username";
//    public static final String COLUMN_PASSWORD = "password";
//    public static final String COLUMN_ROLE = "role"; // Vai trò: "admin" hoặc "user"
//
//    // Bảng thông báo
//    public static final String TABLE_NOTIFICATIONS = "notifications";
//    public static final String COLUMN_NOTIFICATION_ID = "notification_id";
//    public static final String COLUMN_NOTIFICATION_CONTENT = "content";
//    public static final String COLUMN_NOTIFICATION_STATUS = "status"; // "unread", "read"


    public CartDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng giỏ hàng
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_PRICE + " INTEGER, " +
                COLUMN_IMAGE + " INTEGER)";
        db.execSQL(CREATE_CART_TABLE);

        // Tạo bảng đơn hàng
//        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + " (" +
//                COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_ORDER_ADDRESS + " TEXT, " +
//                COLUMN_ORDER_PRODUCT_DETAILS + " TEXT, " +
//                COLUMN_ORDER_TOTAL_PRICE + " INTEGER, " +
//                COLUMN_ORDER_STATUS + " TEXT);";
//
//        db.execSQL(CREATE_ORDERS_TABLE);


        // Tạo bảng order_items
//        String createOrderItemsTable = "CREATE TABLE " + TABLE_ORDER_ITEMS + " (" +
//                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_ITEM_ORDER_ID + " INTEGER, " +
//                COLUMN_ITEM_PRODUCT_NAME + " TEXT, " +
//                COLUMN_ITEM_QUANTITY + " INTEGER, " +
//                COLUMN_ITEM_PRICE + " INTEGER, " +
//                "FOREIGN KEY(" + COLUMN_ITEM_ORDER_ID + ") REFERENCES " + TABLE_ORDERS + "(" + COLUMN_ORDER_ID + "));";
//        db.execSQL(createOrderItemsTable);

        String CREATE_ADDRESS_TABLE = "CREATE TABLE " + TABLE_ADDRESS + " (" +
                COLUMN_ADDRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ADDRESS + " TEXT)";
        db.execSQL(CREATE_ADDRESS_TABLE);

        // Tạo bảng người dùng
//        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
//                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_USERNAME + " TEXT UNIQUE NOT NULL, " +
//                COLUMN_PASSWORD + " TEXT NOT NULL, " +
//                COLUMN_ROLE + " TEXT NOT NULL)";
//        db.execSQL(CREATE_USERS_TABLE);

        // Thêm người dùng mặc định
//        ContentValues adminUser = new ContentValues();
//        adminUser.put(COLUMN_USERNAME, "admin");
//        adminUser.put(COLUMN_PASSWORD, "admin123"); // Lưu ý: mật khẩu cần được mã hóa trong thực tế
//        adminUser.put(COLUMN_ROLE, "admin");
//        db.insert(TABLE_USERS, null, adminUser);

        // Tạo bảng thông báo
//        String CREATE_NOTIFICATIONS_TABLE = "CREATE TABLE " + TABLE_NOTIFICATIONS + " (" +
//                COLUMN_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_NOTIFICATION_CONTENT + " TEXT, " +
//                COLUMN_NOTIFICATION_STATUS + " TEXT)";
//        db.execSQL(CREATE_NOTIFICATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_ITEMS);
//
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
//
//        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NOTIFICATIONS);
        onCreate(db);
    }

}
