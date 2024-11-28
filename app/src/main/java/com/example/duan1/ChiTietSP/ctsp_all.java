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
import com.example.duan1.Fragment.DSDienThoaiFragment;
import com.example.duan1.GioHang.CartActivity;
import com.example.duan1.MainActivity;
import com.example.duan1.Models.CartItem;
import com.example.duan1.R;

import java.text.DecimalFormat;
import java.text.ParseException;

public class ctsp_all extends AppCompatActivity {
        ImageView btnBack, btnCong, btnTru,img_ct;
        TextView tvSoluong, tvTongTien,tvName,tvPrice,tvPhoneRating,tvMota;
        Button btnAdd;
        private int soLuong = 1; // Số lượng mặc định là 1
        private double giaSanPham = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ctsp_all);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        img_ct = findViewById(R.id.img_ct);
        tvPhoneRating = findViewById(R.id.tvPhoneRating);
        tvTongTien = findViewById(R.id.tvTongTien);
        tvSoluong = findViewById(R.id.tvSoluong);
        btnTru = findViewById(R.id.btnTru);
        btnCong = findViewById(R.id.btnCong);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        tvMota = findViewById(R.id.tvMoTa);





//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ctsp_all.this, DSDienThoaiFragment.class);
//                startActivity(intent);
//            }
//        });


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String rating = intent.getStringExtra("rating");
        String moTa = intent.getStringExtra("moTa");
        String priceStr = intent.getStringExtra("price");
        int imageResource = intent.getIntExtra("imageUrl", 0);



        tvMota.setText(moTa);
        tvName.setText(name);
        tvPhoneRating.setText(rating);
        tvPrice.setText(priceStr);

        img_ct.setImageResource(imageResource);



        giaSanPham = parseGia(priceStr);

        tvPrice.setText(priceStr);
        tvTongTien.setText(priceStr);
        tvSoluong.setText(String.valueOf(soLuong));


        btnCong.setOnClickListener(v -> {
            soLuong++;
            updateTongTien();
        });

        // Xử lý nút giảm số lượng
        btnTru.setOnClickListener(v -> {
            if (soLuong > 1) { // Đảm bảo số lượng không nhỏ hơn 1
                soLuong--;
                updateTongTien();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo đối tượng DAO để xử lý giỏ hàng
                CartDAO cartDAO = new CartDAO(ctsp_all.this);

                // Tạo một đối tượng CartItem với thông tin hiện tại
                CartItem cartItem = new CartItem(
                        tvName.getText().toString(), // Lấy tên sản phẩm từ TextView
                        soLuong,                    // Số lượng sản phẩm (đã được cập nhật)
                        (int) giaSanPham,                 // Giá mỗi sản phẩm (số thực)
                        imageResource // Ảnh sản phẩm
                );

                // Thêm sản phẩm vào giỏ hàng qua DAO
                cartDAO.addToCart(cartItem);

                // Hiển thị thông báo
                Toast.makeText(ctsp_all.this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();

                // Chuyển sang màn hình giỏ hàng
                Intent intent = new Intent(ctsp_all.this, CartActivity.class);
                startActivity(intent);
            }
        });

    }



    private double parseGia(String giaStr) {
        giaStr = giaStr.replace("đ", "").replace(".", "").trim(); // Xóa "đ" và dấu phẩy
        try {
            return Double.parseDouble(giaStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }


    private String formatGia(double gia) {
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        return decimalFormat.format(gia);
    }

    // Hàm cập nhật tổng tiền
    private void updateTongTien() {
        double tongTien = soLuong * giaSanPham;
        tvTongTien.setText(formatGia(tongTien));
        tvSoluong.setText(String.valueOf(soLuong));
    }




    }



