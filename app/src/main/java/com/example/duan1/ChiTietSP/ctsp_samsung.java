package com.example.duan1.ChiTietSP;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1.DAO.CartDAO;
import com.example.duan1.GioHang.CartActivity;
import com.example.duan1.Home.Home;
import com.example.duan1.Models.CartItem;
import com.example.duan1.R;

import java.text.DecimalFormat;

public class ctsp_samsung extends AppCompatActivity {

    ImageView btnBack_samsung, btnCong_samsung, btnTru_samsung, btnNext_samsung, img_ct_samsung, btnPrev_samsung;
    TextView tvSoluong_samsung, tvTongTien_samsung;
    Button btnAdd_samsung;
    int k= 1;
    int tong= 29990000;

    int [] images= {
            R.drawable.product_samsung_1,
            R.drawable.product_samsung_2,
            R.drawable.product_samsung_3,
            R.drawable.product_samsung_4
    };

    int index= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ctsp_samsung);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack_samsung= findViewById(R.id.btnBack_samsung);
        tvSoluong_samsung= findViewById(R.id.tvSoluong_samsung);
        tvTongTien_samsung= findViewById(R.id.tvTongTien_samsung);
        btnCong_samsung= findViewById(R.id.btnCong_samsung);
        btnTru_samsung= findViewById(R.id.btnTru_samsung);
        btnNext_samsung= findViewById(R.id.btnNext_samsung);
        img_ct_samsung= findViewById(R.id.img_ct_samsung);
        btnPrev_samsung= findViewById(R.id.btnPrev_samsung);
        btnAdd_samsung =  findViewById(R.id.btnAdd_samsung);



        //nút thoát
        btnBack_samsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctsp_samsung.this, Home.class));
            }
        });

        //chuyển ảnh
        btnPrev_samsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index > 0){
                    index--;

                }else {
                    index= images.length - 1;
                }
                img_ct_samsung.setImageResource(images[index]);
            }
        });

        btnNext_samsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index < images.length - 1){
                    index++;
                }else {
                    index= 0;
                }
                img_ct_samsung.setImageResource(images[index]);
            }
        });

        //thêm số lượng
        tvSoluong_samsung.setText("" +k);
        tvTongTien_samsung.setText(Utils.formatCurrency(tong));


        btnCong_samsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                k++;
                tvSoluong_samsung.setText("" +k);
                tvTongTien_samsung.setText(Utils.formatCurrency(+tong * k));
            }
        });

        btnTru_samsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (k > 1){
                    k--;
                    tvSoluong_samsung.setText("" +k);
                    tvTongTien_samsung.setText(Utils.formatCurrency(+tong * k));
                }
            }
        });
        btnAdd_samsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartDAO cartDAO = new CartDAO(ctsp_samsung.this);

                CartItem cartItem = new CartItem(
                        "Samsung",
                        k, // số lượng
                        tong, // giá mỗi sản phẩm
                        R.drawable.product_samsung_1 // ảnh minh họa
                );

                cartDAO.addToCart(cartItem);
                Toast.makeText(ctsp_samsung.this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();

                // Chuyển sang màn hình giỏ hàng
                Intent intent = new Intent(ctsp_samsung.this, CartActivity.class);
                startActivity(intent); // Chuyển màn hình
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