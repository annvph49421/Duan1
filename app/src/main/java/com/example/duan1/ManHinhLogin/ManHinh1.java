package com.example.duan1.ManHinhLogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1.R;

public class ManHinh1 extends AppCompatActivity {
Button btnnext;
TextView btnBoQua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh1);
        btnnext = findViewById(R.id.btnnext);
        btnBoQua = findViewById(R.id.txtBoQua);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManHinh1.this, Manhinh2.class);
                startActivity(intent);
            }
        });

        btnBoQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManHinh1.this,ManHinhLogin.class);
                startActivity(intent);
            }
        });



    }


}