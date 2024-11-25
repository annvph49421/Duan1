package com.example.duan1.ManHinhTT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TTCaNhan2 extends AppCompatActivity {
    TextView tvName, tvEmail,tvTen,tvEdit;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ttca_nhan2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvEdit = findViewById(R.id.tvEdit);
        tvTen = findViewById(R.id.tvTen);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        auth = FirebaseAuth.getInstance();


        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TTCaNhan2.this,TTCaNhan3.class);
                startActivity(intent);
            }
        });

        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            tvTen.setText(user.getDisplayName());
            tvName.setText("Name: " + user.getDisplayName());
            tvEmail.setText("Email: " + user.getEmail());
        }else{
            tvName.setText("Name: ");
            tvEmail.setText("Email: ");
        }


    }
}