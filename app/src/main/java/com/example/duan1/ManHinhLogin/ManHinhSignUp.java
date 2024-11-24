package com.example.duan1.ManHinhLogin;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;
import com.example.duan1.SQLite.DatabaseHelper;

public class ManHinhSignUp extends AppCompatActivity {

    EditText nameInput, emailInput, passwordInput, retypePasswordInput;
    Button signUpButton;

    private FirebaseAuth mAuth;


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

        mAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                String retypePassword = retypePasswordInput.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(retypePassword)) {
                    Toast.makeText(ManHinhSignUp.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(!password.equals(retypePassword)){
                    Toast.makeText(ManHinhSignUp.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                registerUser(email, password);
            }
        });





    }
    private void registerUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(this,"Dang ky thanh cong",Toast.LENGTH_SHORT).show();
                        Log.d("SignUp", "User ID: " + (user != null ? user.getUid() : "null"));
                        Intent intent = new Intent(ManHinhSignUp.this, ManHinhLogin.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("SignUp", "Error: ", task.getException());
                    }
                });

    }
}
