package com.example.duan1.ManHinhTT;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TTCaNhan3 extends AppCompatActivity {
    EditText edtOldPass, edtNewPass;
    Button btnChange;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ttca_nhan3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtOldPass = findViewById(R.id.edtOldPass);
        edtNewPass = findViewById(R.id.edtNewPass);
        btnChange = findViewById(R.id.btnChange);
        auth = FirebaseAuth.getInstance();

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = edtOldPass.getText().toString().trim();
                String newPassword = edtNewPass.getText().toString().trim();


                if(TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword)){
                    Toast.makeText(TTCaNhan3.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser user = auth.getCurrentUser();
                if (user != null && user.getEmail() != null) {
                    // Xác thực lại người dùng với mật khẩu cũ
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
                    user.reauthenticate(credential).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Cập nhật mật khẩu mới
                            user.updatePassword(newPassword).addOnCompleteListener(updateTask -> {
                                if (updateTask.isSuccessful()) {
                                    Toast.makeText(TTCaNhan3.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(TTCaNhan3.this, "Đổi mật khẩu thất bại: " + updateTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(TTCaNhan3.this, "Mật khẩu cũ không đúng: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(TTCaNhan3.this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}