package com.example.duan1.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Models.QLDT;
import com.example.duan1.SQLite.DbHelperQLDT;

import java.util.ArrayList;

public class QLDTDAO {

    private DbHelperQLDT dbHelperQLDT;

    public QLDTDAO(Context context){
        dbHelperQLDT= new DbHelperQLDT(context);
    }

    public ArrayList<QLDT> getDS(){
        SQLiteDatabase sqLiteDatabase= dbHelperQLDT.getReadableDatabase();

        ArrayList<QLDT> list= new ArrayList<>();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM DANHSACHDT", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                 list.add(new QLDT(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getInt(4)));
            }while (cursor.moveToNext());
        }

        return list;
    }




}
