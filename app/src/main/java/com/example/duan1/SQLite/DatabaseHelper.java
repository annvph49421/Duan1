package com.example.duan1.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Tên cơ sở dữ liệu và phiên bản
    private static final String DATABASE_NAME = "StoreDB";
    private static final int DATABASE_VERSION = 1;

    // Tên các bảng
    public static final String TABLE_NGUOI_DUNG = "NguoiDung";
    public static final String TABLE_VAI_TRO = "VaiTro";
    public static final String TABLE_LOAI_SAN_PHAM = "LoaiSanPham";
    public static final String TABLE_SAN_PHAM = "SanPham";
    public static final String TABLE_DON_HANG = "DonHang";
    public static final String TABLE_DON_HANG_CHI_TIET = "DonHangChiTiet";
    public static final String TABLE_MA_GIAM_GIA = "MaGiamGia";
    public static final String TABLE_NHAN_TIN = "NhanTin";
    public static final String TABLE_DANH_GIA = "DanhGia";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng NguoiDung
        db.execSQL("CREATE TABLE " + TABLE_NGUOI_DUNG + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TenDangNhap TEXT NOT NULL," +
                "MatKhau TEXT NOT NULL," +
                "Ten TEXT," +
                "Tuoi INTEGER," +
                "GioiTinh TEXT," +
                "DiaChi TEXT," +
                "Email TEXT," +
                "SoDienThoai TEXT," +
                "IdVaiTro INTEGER," +
                "FOREIGN KEY (IdVaiTro) REFERENCES VaiTro(Id))");

        // Tạo bảng VaiTro
        db.execSQL("CREATE TABLE " + TABLE_VAI_TRO + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TenVaiTro TEXT NOT NULL)");

        // Tạo bảng LoaiSanPham
        db.execSQL("CREATE TABLE " + TABLE_LOAI_SAN_PHAM + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TenLoaiSanPham TEXT NOT NULL)");

        // Tạo bảng SanPham
        db.execSQL("CREATE TABLE " + TABLE_SAN_PHAM + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TenSanPham TEXT NOT NULL," +
                "GiaSanPham REAL NOT NULL," +
                "SoLuongSanPham INTEGER NOT NULL," +
                "MoTaSanPham TEXT," +
                "IdLoaiSanPham INTEGER," +
                "FOREIGN KEY (IdLoaiSanPham) REFERENCES LoaiSanPham(Id))");

        // Tạo bảng DonHang
        db.execSQL("CREATE TABLE " + TABLE_DON_HANG + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "IdNguoiDung INTEGER," +
                "ThoiGianDatHang TEXT," +
                "TongTien REAL NOT NULL," +
                "FOREIGN KEY (IdNguoiDung) REFERENCES NguoiDung(Id))");

        // Tạo bảng DonHangChiTiet
        db.execSQL("CREATE TABLE " + TABLE_DON_HANG_CHI_TIET + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "IdDonHang INTEGER," +
                "IdSanPham INTEGER," +
                "SoLuongSanPham INTEGER NOT NULL," +
                "GiaSanPham REAL NOT NULL," +
                "FOREIGN KEY (IdDonHang) REFERENCES DonHang(Id)," +
                "FOREIGN KEY (IdSanPham) REFERENCES SanPham(Id))");

        // Tạo bảng MaGiamGia
        db.execSQL("CREATE TABLE " + TABLE_MA_GIAM_GIA + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TenMa TEXT NOT NULL," +
                "PhanTramGiam REAL NOT NULL," +
                "ThoiGianHetHan TEXT," +
                "ThoiGianTao TEXT)");

        // Tạo bảng NhanTin
        db.execSQL("CREATE TABLE " + TABLE_NHAN_TIN + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "IdNguoiDung INTEGER," +
                "NoiDung TEXT NOT NULL," +
                "ThoiGianNhan TEXT," +
                "FOREIGN KEY (IdNguoiDung) REFERENCES NguoiDung(Id))");

        // Tạo bảng DanhGia
        db.execSQL("CREATE TABLE " + TABLE_DANH_GIA + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "IdNguoiDung INTEGER," +
                "IdSanPham INTEGER," +
                "DiemDanhGia INTEGER NOT NULL," +
                "BinhLuan TEXT," +
                "ThoiGianDanhGia TEXT," +
                "FOREIGN KEY (IdNguoiDung) REFERENCES NguoiDung(Id)," +
                "FOREIGN KEY (IdSanPham) REFERENCES SanPham(Id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NGUOI_DUNG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VAI_TRO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOAI_SAN_PHAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAN_PHAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DON_HANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DON_HANG_CHI_TIET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MA_GIAM_GIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NHAN_TIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANH_GIA);
        onCreate(db);
    }

    public boolean addUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Kiểm tra xem email đã tồn tại chưa
        Cursor cursor = db.rawQuery("SELECT * FROM NguoiDung WHERE Email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false; // Email đã tồn tại
        }

        // Thêm tài khoản mới vào cơ sở dữ liệu
        ContentValues values = new ContentValues();
        values.put("TenDangNhap", name);
        values.put("Email", email);
        values.put("MatKhau", password);

        long result = db.insert("NguoiDung", null, values);
        cursor.close();
        db.close();

        return result != -1; // Trả về true nếu thêm thành công
    }

    // Phương thức kiểm tra thông tin đăng nhập
    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NGUOI_DUNG +
                " WHERE Email = ? AND MatKhau = ?", new String[]{email, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }
    public boolean checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Truy vấn để kiểm tra email và mật khẩu trong cơ sở dữ liệu
        String query = "SELECT * FROM NguoiDung WHERE Email = ? AND MatKhau = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        // Kiểm tra xem có bản ghi khớp với thông tin đăng nhập không
        boolean isUserExists = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isUserExists;
    }
}
