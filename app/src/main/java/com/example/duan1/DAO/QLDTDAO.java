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
        this.context= context;
        dbHelperQLDT= new DbHelperQLDT(context);
    }

    public ArrayList<QLDT> getDS(){
        SQLiteDatabase sqLiteDatabase= dbHelperQLDT.getReadableDatabase();

        ArrayList<QLDT> list= new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM DANHSACHDT", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String imgName = cursor.isNull(5) ? null : cursor.getString(5);
                Log.d("CursorData", "Image name: " + imgName);
                list.add(new QLDT(
                        cursor.getInt(0),  // madt
                        cursor.getString(1),  // tendt
                        cursor.getString(2),  // sao
                        cursor.getString(3),  // dungluong
                        cursor.getInt(4),  // gia
                        imgName  // Image path
                ));
            } while (cursor.moveToNext());
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
        contentValues.put("image1", imgPath);

        Log.d("Debug", "Image Path: " + imgPath);
        Log.d("Debug", "Image URI: " + imgUri);



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
        contentValues.put("image1", imgPath);

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
    private String saveImageToStorage(Uri imgUri) {
        try {
            if (imgUri == null) {
                Log.e("SaveImageError", "Image URI is null");
                return null;
            }

            InputStream inputStream = context.getContentResolver().openInputStream(imgUri);
            if (inputStream == null) {
                Log.e("SaveImageError", "InputStream is null for URI: " + imgUri);
                return null;
            }

            File file = new File(context.getFilesDir(), "images");
            if (!file.exists() && !file.mkdirs()) {
                Log.e("SaveImageError", "Failed to create directory: " + file.getAbsolutePath());
                return null;
            }

            File imageFile = new File(file, System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(imageFile);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            outputStream.close();
            inputStream.close();

            Log.d("SaveImageDebug", "Image saved to: " + imageFile.getAbsolutePath());
            return imageFile.getAbsolutePath();
        } catch (Exception e) {
            Log.e("SaveImageError", "Error saving image", e);
            return null;
        }
    }


}

