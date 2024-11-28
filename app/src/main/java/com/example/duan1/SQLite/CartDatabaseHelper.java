package com.example.duan1.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CartDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CartDatabase.db";
    private static final int DATABASE_VERSION = 13  ;

    public static final String TABLE_CART = "cart";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_IMAGE = "image";

    // Bảng địa chỉ
    public static final String TABLE_ADDRESS = "address";
    public static final String COLUMN_ADDRESS_ID = "id";
    public static final String COLUMN_ADDRESS = "address";

    // Bảng đơn hàng
    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_ORDER_ADDRESS = "address";
    public static final String COLUMN_ORDER_TOTAL_PRICE = "total_price";
    public static final String COLUMN_ORDER_STATUS = "status";

    public CartDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_PRICE + " INTEGER, " +
                COLUMN_IMAGE + " INTEGER)";
        db.execSQL(CREATE_CART_TABLE);

        // Tạo bảng địa chỉ
        String CREATE_ADDRESS_TABLE = "CREATE TABLE " + TABLE_ADDRESS + " (" +
                COLUMN_ADDRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ADDRESS + " TEXT)";
        db.execSQL(CREATE_ADDRESS_TABLE);

        // Tạo bảng đơn hàng
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + " (" +
                COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORDER_ADDRESS + " TEXT, " +
                COLUMN_ORDER_TOTAL_PRICE + " REAL, " +
                COLUMN_ORDER_STATUS + " TEXT)";
        db.execSQL(CREATE_ORDERS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }
    // Phương thức để thêm đơn hàng vào bảng orders
    public void addOrder(String address, double totalPrice, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_ADDRESS, address);
        values.put(COLUMN_ORDER_TOTAL_PRICE, totalPrice);
        values.put(COLUMN_ORDER_STATUS, status); // Trạng thái mặc định là "pending"

        try {
            db.insert(TABLE_ORDERS, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    // Phương thức để lấy tất cả các đơn hàng
    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ORDERS, null, null, null, null, null, null);
    }

    // Phương thức để lấy thông tin đơn hàng theo ID
    public Cursor getOrderById(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_ORDER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(orderId)};
        return db.query(TABLE_ORDERS, null, selection, selectionArgs, null, null, null);
    }
    // Xóa sản phẩm khỏi giỏ hàng
    public void removeProductFromCart(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, null, null); // Xóa tất cả sản phẩm trong bảng giỏ hàng
        db.close();
    }
}