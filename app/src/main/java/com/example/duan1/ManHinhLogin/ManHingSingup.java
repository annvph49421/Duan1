package com.example.duan1.ManHinhLogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1.R;

public class ManHingSingup extends AppCompatActivity {

    private EditText nameInput, emailInput, passwordInput, retypePasswordInput;
    private Button signUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Ánh xạ các thành phần giao diện
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        retypePasswordInput = findViewById(R.id.retypePasswordInput);
        signUpButton = findViewById(R.id.signUpButton);


        // Xử lý sự kiện nút "Sign Up"
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                String retypePassword = retypePasswordInput.getText().toString().trim();

                if (validateInputs(name, email, password, retypePassword)) {
                    // Thực hiện logic đăng ký tài khoản tại đây
                    Toast.makeText(ManHingSingup.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

                    // Điều hướng sang màn hình khác hoặc quay lại màn hình đăng nhập
                    Intent intent = new Intent(ManHingSingup.this, ManHinhLogin.class);
                    startActivity(intent);
                    finish();
                }
            }
        });



    }

    // Hàm kiểm tra tính hợp lệ của dữ liệu nhập vào
    private boolean validateInputs(String name, String email, String password, String retypePassword) {
        if (name.isEmpty()) {
            nameInput.setError("Please enter your name");
            return false;
        }
        if (email.isEmpty()) {
            emailInput.setError("Please enter your email");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Please enter a valid email");
            return false;
        }
        if (password.isEmpty()) {
            passwordInput.setError("Please enter a password");
            return false;
        }
        if (password.length() < 6) {
            passwordInput.setError("Password must be at least 6 characters");
            return false;
        }
        if (!password.equals(retypePassword)) {
            retypePasswordInput.setError("Passwords do not match");
            return false;
        }
        return true;
    }

}