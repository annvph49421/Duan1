package com.example.duan1.DAO;

import static java.security.AccessController.getContext;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.duan1.Models.QLDT;
import com.example.duan1.SQLite.DbHelperQLDT;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class QLDTDAO {

    private DbHelperQLDT dbHelperQLDT;
    private Context context;

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
                 list.add(new QLDT(
                         cursor.getInt(0),
                         cursor.getString(1),
                         cursor.getString(2),
                         cursor.getString(3),
                         cursor.getInt(4),
                         cursor.isNull(5) ? null : cursor.getString(5)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    //add dt
    public boolean themDTMoi(QLDT qldt, Uri imgUri){
        SQLiteDatabase sqLiteDatabase= dbHelperQLDT.getWritableDatabase();

        String imgPath= saveImageToStorage(imgUri);

        ContentValues contentValues= new ContentValues();
        contentValues.put("tendt", qldt.getTendt());
        contentValues.put("sao", qldt.getSao());
        contentValues.put("dungluong", qldt.getDungluong());
        contentValues.put("gia", qldt.getGia());
        contentValues.put("image", imgPath);

        long check= sqLiteDatabase.insert("DANHSACHDT", null, contentValues);
        if (check == -1) return false;
        return true;
    }

    //sửa
    public boolean suaDT(QLDT qldt, Uri imgUri){
        SQLiteDatabase sqLiteDatabase= dbHelperQLDT.getWritableDatabase();

        String imgPath= saveImageToStorage(imgUri);

        ContentValues contentValues= new ContentValues();
        contentValues.put("tendt", qldt.getTendt());
        contentValues.put("sao", qldt.getSao());
        contentValues.put("dungluong", qldt.getDungluong());
        contentValues.put("gia", qldt.getGia());
        contentValues.put("image", imgPath);

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

    //thêm ảnh
    private String saveImageToStorage(Uri imageUri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "image_" + System.currentTimeMillis() + ".jpg");

            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            return file.getAbsolutePath();  // trả về đường dẫn file hình ảnh
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

}
}

