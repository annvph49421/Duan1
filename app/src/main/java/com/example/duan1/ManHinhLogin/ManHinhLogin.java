package com.example.duan1.ManHinhLogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1.R;

public class ManHinhLogin extends AppCompatActivity {
EditText edemail,edpassword;
ImageView eyeIcon;
CheckBox cbremember;
TextView txtforgotpassword, txtsingup;
Button btnlogin;
boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_login);
        // Ánh xạ các thành phần giao diện
        edemail = findViewById(R.id.edemail);
        edpassword = findViewById(R.id.edpassword);
        btnlogin = findViewById(R.id.btnlogin);
        cbremember = findViewById(R.id.cb);
        txtforgotpassword = findViewById(R.id.txtfogotpassword);
        txtsingup = findViewById(R.id.txtsingup);


        // Xử lý sự kiện nút Đăng nhập
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edemail.getText().toString().trim();
                String password = edpassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(ManHinhLogin.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Thực hiện logic đăng nhập ở đây
                    // Ví dụ: Xác thực email và mật khẩu
                    Toast.makeText(ManHinhLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện nhấn vào "Forgot Password"
        txtforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng hoặc hiển thị thông báo
                Toast.makeText(ManHinhLogin.this, "Forgot Password clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện nhấn vào "Sign Up"
        txtsingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Chuyển hướng sang màn hình đăng ký
                Toast.makeText(ManHinhLogin.this, "Sign Up clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện nhấn vào biểu tượng mắt để hiển thị/ẩn mật khẩu
//        eyeIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isPasswordVisible) {
//                    // Ẩn mật khẩu
//                    edpassword.setInputType(129); // InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
//                    eyeIcon.setImageResource(R.drawable.img_9); // Đổi thành biểu tượng mắt đóng
//                    isPasswordVisible = false;
//                } else {
//                    // Hiển thị mật khẩu
//                    edpassword.setInputType(145); // InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//                    eyeIcon.setImageResource(R.drawable.img_9); // Đổi thành biểu tượng mắt mở
//                    isPasswordVisible = true;
//                }
//                // Đặt con trỏ văn bản ở cuối văn bản
//                edpassword.setSelection(edpassword.getText().length());
//            }
//        });

    }
}