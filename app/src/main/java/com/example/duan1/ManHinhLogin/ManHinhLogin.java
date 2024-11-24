package com.example.duan1.ManHinhLogin;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.Home.Home;
import com.example.duan1.R;
import com.example.duan1.SQLite.DatabaseHelper;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class ManHinhLogin extends AppCompatActivity {
    EditText edemail, edpassword;
    ImageView eyeIcon;
    CheckBox cbremember;
    TextView txtforgotpassword, txtsingup;
    Button btnlogin;
    boolean isPasswordVisible = false;
    private FirebaseAuth mAuth;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_login);

        // Ánh xạ các thành phần giao diện
        edemail = findViewById(R.id.edemail);
        edpassword = findViewById(R.id.edpassword);
        btnlogin = findViewById(R.id.btnlogin);
        cbremember = findViewById(R.id.cb);
        txtforgotpassword = findViewById(R.id.txtfogotpassword);
        txtsingup = findViewById(R.id.txtsingup);
        eyeIcon = findViewById(R.id.eyeIcon); // Ánh xạ biểu tượng con mắt

        // Xử lý sự kiện nhấn vào "Đăng nhập"

        mAuth = FirebaseAuth.getInstance();

         preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedEmail = preferences.getString("email", null);
        boolean rememberMe = preferences.getBoolean("rememberMe", false);

        if (savedEmail != null && rememberMe) {
            edemail.setText(savedEmail);
            cbremember.setChecked(true);
        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edemail.getText().toString().trim();
                String password = edpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(ManHinhLogin.this,"Dien day du thong tin",Toast.LENGTH_SHORT).show();
                    return;
                }
                loginUser(email,password);
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
                Intent intent = new Intent(ManHinhLogin.this, ManHinhSignUp.class);
                startActivity(intent); // Chuyển sang màn hình đăng ký
            }
        });

        // Xử lý sự kiện nhấn vào biểu tượng mắt để hiển thị/ẩn mật khẩu
        eyeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Ẩn mật khẩu
                    edpassword.setInputType(129); // InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    eyeIcon.setImageResource(R.drawable.img_9); // Đổi thành biểu tượng mắt đóng
                    isPasswordVisible = false;
                } else {
                    // Hiển thị mật khẩu
                    edpassword.setInputType(145); // InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    eyeIcon.setImageResource(R.drawable.img_9); // Đổi thành biểu tượng mắt mở
                    isPasswordVisible = true;
                }
                // Đặt con trỏ văn bản ở cuối văn bản
                edpassword.setSelection(edpassword.getText().length());
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        SharedPreferences.Editor editor = preferences.edit();
                        if (cbremember.isChecked()) {
                            editor.putString("email", email);
                            editor.putBoolean("rememberMe", true);
                        } else {
                            editor.clear();
                        }
                        editor.apply();
                        Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ManHinhLogin.this, Home.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
