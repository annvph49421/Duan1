package com.example.duan1.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelperQLDT extends SQLiteOpenHelper {
    public DbHelperQLDT(Context context){
        super(context, "QLDT", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
         String qDSDT= "CREATE TABLE DANHSACHDT(madt INTEGER PRIMARY KEY AUTOINCREMENT, tendt TEXT, sao DOUBLE, dungluong TEXT, gia INTEGER)";
         sqLiteDatabase.execSQL(qDSDT);

         String dDSDT= "INSERT INTO DANHSACHDT VALUES(1, 'Iphone 15 Pro Max', 5, '256GB', 31000000), (2, 'OPPO Reno12', 4.7, '64GB', 12290000)";
         sqLiteDatabase.execSQL(dDSDT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS DANHSACHDT");
            onCreate(sqLiteDatabase);
        }
    }
}
