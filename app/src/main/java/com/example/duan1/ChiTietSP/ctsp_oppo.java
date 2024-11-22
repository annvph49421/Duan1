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

import com.example.duan1.Adapter.ProductAdapter;
import com.example.duan1.DAO.CartDAO;
import com.example.duan1.GioHang.CartActivity;
import com.example.duan1.Home.Home;

import com.example.duan1.Models.CartItem;
import com.example.duan1.R;

import java.text.DecimalFormat;

public class                                                                                                                                                                        ctsp_oppo extends AppCompatActivity {
    private static Utils Utils;
    ImageView btnBack_oppo, btnCong_oppo, btnTru_oppo, btnNext_oppo, img_ct_oppo, btnPrev_oppo;
    TextView tvSoluong_oppo, tvTongTien_oppo;
    Button btnAdd_oppo;
    int k= 1;
    int tong= 12290000;
    ProductAdapter product;


    int [] images= {
            R.drawable.product_oppo12_1,
            R.drawable.product_oppo12_2,
            R.drawable.product_oppo12_3,
            R.drawable.product_oppo12_4
    };

    int index= 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ctsp_oppo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack_oppo= findViewById(R.id.btnBack_oppo);
        tvSoluong_oppo= findViewById(R.id.tvSoluong_oppo);
        tvTongTien_oppo= findViewById(R.id.tvTongTien_oppo);
        btnCong_oppo= findViewById(R.id.btnCong_oppo);
        btnTru_oppo= findViewById(R.id.btnTru_oppo);
        btnNext_oppo= findViewById(R.id.btnNext_oppo);
        img_ct_oppo= findViewById(R.id.img_ct_oppo);
        btnPrev_oppo= findViewById(R.id.btnPrev_oppo);
        btnAdd_oppo = findViewById(R.id.btnAdd_oppo);



        //nút thoát
        btnBack_oppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctsp_oppo.this, Home.class));
            }
        });

        //chuyển ảnh
        btnPrev_oppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index > 0){
                    index--;

                }else {
                    index= images.length - 1;
                }
                img_ct_oppo.setImageResource(images[index]);
            }
        });

        btnNext_oppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index < images.length - 1){
                    index++;
                }else {
                    index= 0;
                }
                img_ct_oppo.setImageResource(images[index]);
            }
        });

        //thêm số lượng
        tvSoluong_oppo.setText("" +k);
        tvTongTien_oppo.setText(ctsp_oppo.Utils.formatCurrency(tong));


        btnCong_oppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                k++;
                tvSoluong_oppo.setText("" +k);
                tvTongTien_oppo.setText(ctsp_oppo.Utils.formatCurrency(+tong * k));
            }
        });

        btnTru_oppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (k > 1){
                    k--;
                    tvSoluong_oppo.setText("" +k);
                    tvTongTien_oppo.setText(ctsp_oppo.Utils.formatCurrency(+tong * k));
                }
            }
        });
        btnAdd_oppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartDAO cartDAO = new CartDAO(ctsp_oppo.this);

                CartItem cartItem = new CartItem(
                        "OPPO Reno12",
                        k, // số lượng
                        tong, // giá mỗi sản phẩm
                        R.drawable.product_oppo12_1 // ảnh minh họa
                );

                cartDAO.addToCart(cartItem);
                Toast.makeText(ctsp_oppo.this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();

                // Chuyển sang màn hình giỏ hàng
                Intent intent = new Intent(ctsp_oppo.this, CartActivity.class);
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