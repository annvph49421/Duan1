package com.example.duan1.ChiTietSP;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1.Home.Home;
import com.example.duan1.R;

import java.text.DecimalFormat;

public class ctsp_ip extends AppCompatActivity {
    ImageView btnBackip, btnCong_ip, btnTru_ip, btnNext_ip, img_ct_ip, btnPrev_ip;
    TextView tvSoluong_ip, tvTongTien_ip;
    int k= 1;
    int tong= 29490000;

    int [] images= {
            R.drawable.product_ip15promax_1,
            R.drawable.product_ip15promax_2,
            R.drawable.product_ip15promax_3,
            R.drawable.product_ip15promax_4
    };

    int index= 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ctsp_ip);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnBackip= findViewById(R.id.btnBack_ip);
        tvSoluong_ip= findViewById(R.id.tvSoluong_ip);
        tvTongTien_ip= findViewById(R.id.tvTongTien_ip);
        btnCong_ip= findViewById(R.id.btnCong_ip);
        btnTru_ip= findViewById(R.id.btnTru_ip);
        btnNext_ip= findViewById(R.id.btnNext_ip);
        img_ct_ip= findViewById(R.id.img_ct_ip);
        btnPrev_ip= findViewById(R.id.btnPrev_ip);



        //nút thoát
        btnBackip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctsp_ip.this, Home.class));
            }
        });

        //chuyển ảnh
        btnPrev_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index > 0){
                    index--;

                }else {
                    index= images.length - 1;
                }
                img_ct_ip.setImageResource(images[index]);
            }
        });

        btnNext_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index < images.length - 1){
                    index++;
                }else {
                    index= 0;
                }
                img_ct_ip.setImageResource(images[index]);
            }
        });

        //thêm số lượng
        tvSoluong_ip.setText("" +k);
        tvTongTien_ip.setText(Utils.formatCurrency(tong));


        btnCong_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                k++;
                tvSoluong_ip.setText("" +k);
                tvTongTien_ip.setText(Utils.formatCurrency(+tong * k));
            }
        });

        btnTru_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (k > 1){
                    k--;
                    tvSoluong_ip.setText("" +k);
                    tvTongTien_ip.setText(Utils.formatCurrency(+tong / k));
                }
            }
        });

    }

    public static class Utils {
        public static String formatCurrency(int amount) {
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            return formatter.format(amount) + "đ";
        }
    }
}

