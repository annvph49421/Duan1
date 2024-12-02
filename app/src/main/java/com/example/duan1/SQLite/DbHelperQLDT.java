package com.example.duan1.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class DbHelperQLDT extends SQLiteOpenHelper {
    public DbHelperQLDT(Context context){
        super(context, "QLDT4", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
         String qDSDT= "CREATE TABLE DANHSACHDT(madt INTEGER PRIMARY KEY AUTOINCREMENT, tendt TEXT, sao DOUBLE, dungluong TEXT, gia INTEGER, image1 TEXT NOT NULL)";
         sqLiteDatabase.execSQL(qDSDT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS DANHSACHDT");
            onCreate(sqLiteDatabase);
        }
    }





}


