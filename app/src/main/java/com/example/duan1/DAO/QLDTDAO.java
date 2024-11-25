package com.example.duan1.DAO;

import android.content.ContentValues;
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
                 list.add(new QLDT(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    //add dt
    public boolean themDTMoi(QLDT qldt){
        SQLiteDatabase sqLiteDatabase= dbHelperQLDT.getWritableDatabase();

        ContentValues contentValues= new ContentValues();
        contentValues.put("tendt", qldt.getTendt());
        contentValues.put("sao", qldt.getSao());
        contentValues.put("dungluong", qldt.getDungluong());
        contentValues.put("gia", qldt.getGia());

        long check= sqLiteDatabase.insert("DANHSACHDT", null, contentValues);
        if (check == -1) return false;
        return true;
    }

    //sửa
    public boolean suaDT(QLDT qldt){
        SQLiteDatabase sqLiteDatabase= dbHelperQLDT.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("tendt", qldt.getTendt());
        contentValues.put("sao", qldt.getSao());
        contentValues.put("dungluong", qldt.getDungluong());
        contentValues.put("gia", qldt.getGia());

        int check= sqLiteDatabase.update("DANHSACHDT", contentValues, "madt= ?", new String[]{String.valueOf(qldt.getMadt())});
        if (check <= 0) return false;
        return true;
    }

    //xóa
    public boolean xoaDT(int madt){
        SQLiteDatabase sqLiteDatabase= dbHelperQLDT.getWritableDatabase();

        int check= sqLiteDatabase.delete("DANHSACHDT", "madt= ?", new String[]{String.valueOf(madt)});

        if (check <= 0) return false;
        return true;
    }




}
