package com.example.duan1.ManHinhLogin;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;
import com.example.duan1.SQLite.DatabaseHelper;

public class ManHinhSignUp extends AppCompatActivity {

    EditText nameInput, emailInput, passwordInput, retypePasswordInput;
    Button signUpButton;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_sign_up);

        // Ánh xạ các thành phần giao diện
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        retypePasswordInput = findViewById(R.id.retypePasswordInput);
        signUpButton = findViewById(R.id.signUpButton);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Kiểm tra kết nối cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null) {
            Log.d("Database", "Connected successfully");
        } else {
            Log.e("Database", "Connection failed");
        }

        // Xử lý sự kiện nhấn vào nút "Sign Up"
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                String retypePassword = retypePasswordInput.getText().toString().trim();

                // Kiểm tra các trường không rỗng
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || retypePassword.isEmpty()) {
                    Toast.makeText(ManHinhSignUp.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra mật khẩu và xác nhận mật khẩu
                if (!password.equals(retypePassword)) {
                    Toast.makeText(ManHinhSignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thêm người dùng vào cơ sở dữ liệu
                ContentValues values = new ContentValues();
                values.put("TenDangNhap", email); // Lưu email làm tên đăng nhập
                values.put("MatKhau", password); // Lưu mật khẩu
                values.put("Ten", name); // Lưu tên người dùng

                long result = db.insert("NguoiDung", null, values);

                if (result != -1) {
                    Toast.makeText(ManHinhSignUp.this, "Sign Up successful", Toast.LENGTH_SHORT).show();
                    Log.d("SignUp", "User added with ID: " + result);

                    // Chuyển sang màn hình đăng nhập sau khi đăng ký thành công
                    Intent intent = new Intent(ManHinhSignUp.this, ManHinhLogin.class);
                    startActivity(intent);
                    finish(); // Đóng màn hình đăng ký
                } else {
                    Toast.makeText(ManHinhSignUp.this, "Sign Up failed", Toast.LENGTH_SHORT).show();
                    Log.e("SignUp", "Error inserting user into database");
                }
            }
        });
    }
}
