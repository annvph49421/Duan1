
package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Models.AddressItem;
import com.example.duan1.SQLite.CartDatabaseHelper;

public class AddressDAO {
    private SQLiteDatabase database;
    private CartDatabaseHelper dbHelper;

    public AddressDAO(Context context) {
        dbHelper = new CartDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void saveAddress(AddressItem addressItem) {
        ContentValues values = new ContentValues();
        values.put(CartDatabaseHelper.COLUMN_ADDRESS, addressItem.getAddress());
        database.insert(CartDatabaseHelper.TABLE_ADDRESS, null, values);
    }

    public AddressItem getAddress() {
        Cursor cursor = database.query(CartDatabaseHelper.TABLE_ADDRESS, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String address = cursor.getString(cursor.getColumnIndex(CartDatabaseHelper.COLUMN_ADDRESS));
            cursor.close();
            return new AddressItem(address);
        }
        return null;
    }
}
