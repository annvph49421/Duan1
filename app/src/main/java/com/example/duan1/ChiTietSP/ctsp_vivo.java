package com.example.duan1.ChiTietSP;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1.Home.Home;
import com.example.duan1.R;

import java.text.DecimalFormat;

public class ctsp_vivo extends AppCompatActivity {
    ImageView btnBack_vivo, btnCong_vivo, btnTru_vivo, btnNext_vivo, img_ct_vivo, btnPrev_vivo;
    TextView tvSoluong_vivo, tvTongTien_vivo;
    int k= 1;
    int tong= 9490000;

    int [] images= {
            R.drawable.product_vivo_1,
            R.drawable.product_vivo_2,
            R.drawable.product_vivo_3,
            R.drawable.product_vivo_4
    };

    int index= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ctsp_vivo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack_vivo= findViewById(R.id.btnBack_vivo);
        tvSoluong_vivo= findViewById(R.id.tvSoluong_vivo);
        tvTongTien_vivo= findViewById(R.id.tvTongTien_vivo);
        btnCong_vivo= findViewById(R.id.btnCong_vivo);
        btnTru_vivo= findViewById(R.id.btnTru_vivo);
        btnNext_vivo= findViewById(R.id.btnNext_vivo);
        img_ct_vivo= findViewById(R.id.img_ct_vivo);
        btnPrev_vivo= findViewById(R.id.btnPrev_vivo);



        //nút thoát
        btnBack_vivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctsp_vivo.this, Home.class));
            }
        });

        //chuyển ảnh
        btnPrev_vivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index > 0){
                    index--;

                }else {
                    index= images.length - 1;
                }
                img_ct_vivo.setImageResource(images[index]);
            }
        });

        btnNext_vivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index < images.length - 1){
                    index++;
                }else {
                    index= 0;
                }
                img_ct_vivo.setImageResource(images[index]);
            }
        });

        //thêm số lượng
        tvSoluong_vivo.setText("" +k);
        tvTongTien_vivo.setText(ctsp_ip.Utils.formatCurrency(tong));


        btnCong_vivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                k++;
                tvSoluong_vivo.setText("" +k);
                tvTongTien_vivo.setText(ctsp_ip.Utils.formatCurrency(+tong * k));
            }
        });

        btnTru_vivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (k > 1){
                    k--;
                    tvSoluong_vivo.setText("" +k);
                    tvTongTien_vivo.setText(ctsp_ip.Utils.formatCurrency(+tong * k));
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