package com.example.duan1.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CartDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CartDatabase.db";
    private static final int DATABASE_VERSION = 5   ;

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


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        onCreate(db);
    }
}