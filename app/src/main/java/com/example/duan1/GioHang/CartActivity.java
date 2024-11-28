package com.example.duan1.GioHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.Adapter.CartAdapter;
import com.example.duan1.DAO.CartDAO;
import com.example.duan1.Home.Home;
import com.example.duan1.Models.CartItem;
import com.example.duan1.R;
import com.example.duan1.SQLite.CartDatabaseHelper;

import java.text.DecimalFormat;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ListView cartListView;
    private CartAdapter cartAdapter;
    private CartDAO cartDAO;
    private List<CartItem> cartItems;
    private TextView totalPriceTextView;
    ImageView btnBack_ip,btnsua;
    private TextView addressTextView;
    private double totalPrice = 0;

    private ImageView btnAddAddress;
    Button btndathang;
    private String address;
    private CartDatabaseHelper cartDatabaseHelper;
    //private TextView emptyCartTextView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        btnBack_ip = findViewById(R.id. btnBack_ip);
        //nút thoát
        btnBack_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, Home.class));
            }
        });

        cartListView = findViewById(R.id.cartListView);
        totalPriceTextView = findViewById(R.id.tvTotalPrice);
        btnAddAddress = findViewById(R.id.btnAddAddress);
        addressTextView = findViewById(R.id.addressTextView);
        btndathang = findViewById(R.id.btndathang);
        btnsua = findViewById(R.id.btnsua);

        cartDatabaseHelper = new CartDatabaseHelper(this);
        cartListView.setAdapter(cartAdapter);



    // TextView hiển thị tổng giá trị
        cartDAO = new CartDAO(this);
        address = cartDAO.getAddress();

        // Lấy dữ liệu giỏ hàng
        cartItems = cartDAO.getCartItems();

        // Tạo adapter và gán vào ListView
        cartAdapter = new CartAdapter(this, cartItems);
        cartListView.setAdapter(cartAdapter);
        // Tính toán tổng giá trị giỏ hàng
        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }


        // Cập nhật tổng giá trị giỏ hàng
        updateTotalPrice();
        // Nếu có địa chỉ, hiển thị nó
        if (address != null) {
            addressTextView.setText(address);
            btnAddAddress.setVisibility(View.GONE);  // Ẩn nút thêm địa chỉ nếu đã có
        } else {
            addressTextView.setText("Thêm địa chỉ nhận hàng");
        }

        // Xử lý sự kiện thêm địa chỉ
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở màn hình thêm địa chỉ
                Intent intent = new Intent(CartActivity.this, AddAddressActivity.class);
                startActivityForResult(intent, 1);  // Request code = 1
            }
        });
        btnsua.setOnClickListener(v -> {
            // Mở EditAddressActivity để sửa địa chỉ
            Intent intent = new Intent(CartActivity.this, EditAddressActivity.class);
            startActivityForResult(intent, 1); // Request code = 1
        });


        // Sự kiện khi nhấn nút "Đặt Hàng"
        // Trong CartActivity
        btndathang.setOnClickListener(v -> {
            // Lưu tất cả sản phẩm trong giỏ vào database (nếu cần)
            for (CartItem item : cartItems) {
                cartDAO.addToCart(item);
            }

            // Truyền thông tin đơn hàng đến OrderDetailsActivity
            String address = addressTextView.getText().toString();
            String productDetails = getProductDetails();  // Hàm lấy danh sách sản phẩm
            int totalPrice = calculateTotalPrice();  // Hàm tính tổng tiền
            String orderStatus = "Chờ phê duyệt";

            Intent intent = new Intent(CartActivity.this, OrderConfirmationActivity.class);
            intent.putExtra("address", address);
            intent.putExtra("productDetails", productDetails);
            intent.putExtra("totalPrice", totalPrice);
            intent.putExtra("orderStatus", orderStatus);
            startActivity(intent);
        });


    }

    // Phương thức cập nhật tổng giá trị giỏ hàng
    public void updateTotalPrice() {
        int totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getPrice();  // Tính tổng giá trị giỏ hàng
        }
        totalPriceTextView.setText("Tổng cộng: " + formatGia(totalPrice) + "đ");  // Hiển thị tổng giá trị
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Nhận thông tin địa chỉ đã sửa từ EditAddressActivity
            String name = data.getStringExtra("name");
            String address = data.getStringExtra("address");
            String phone = data.getStringExtra("phone");

            // Cập nhật địa chỉ trên CartActivity
            addressTextView.setText("Tên: " + name + "\nĐịa chỉ: " + address + "\nSĐT: " + phone);

            // Lưu địa chỉ mới vào cơ sở dữ liệu
            CartDAO cartDAO = new CartDAO(this);
            cartDAO.addAddress(address); // Cập nhật địa chỉ trong cơ sở dữ liệu
        }
    }
//    private void updateCartUI() {
//        if (cartItems.isEmpty()) {
//            emptyCartTextView.setVisibility(View.VISIBLE); // Hiển thị thông báo giỏ hàng trống
//            cartListView.setVisibility(View.GONE); // Ẩn danh sách sản phẩm
//        } else {
//            emptyCartTextView.setVisibility(View.GONE); // Ẩn thông báo giỏ hàng trống
//            cartListView.setVisibility(View.VISIBLE); // Hiển thị danh sách sản phẩm
//        }
//    }
//
//    private void proceedToOrderConfirmation() {
//        // Xóa tất cả sản phẩm trong giỏ hàng
//        cartDatabaseHelper.clearCart();
//
//        // Cập nhật lại giao diện giỏ hàng
//        cartItems.clear();
//        cartAdapter.notifyDataSetChanged();
//        updateCartUI();
//    }
// Hàm lấy chi tiết sản phẩm từ giỏ hàng dưới dạng chuỗi
// Hàm tính tổng giá trị của giỏ hàng
private int calculateTotalPrice() {
    int totalPrice = 0;
    for (CartItem item : cartItems) {
        // Tính giá trị của từng sản phẩm và cộng vào tổng tiền
        totalPrice += item.getPrice() * item.getQuantity();
    }
    return totalPrice;
}
private String getProductDetails() {
    StringBuilder productDetails = new StringBuilder();
    for (CartItem item : cartItems) {
        // Thêm tên sản phẩm, số lượng và giá vào chuỗi
        productDetails.append(item.getProductName())
                .append(" (x").append(item.getQuantity()).append(") - ")
                .append(item.getPrice()).append("đ\n");
    }
    return productDetails.toString();
}


    private String formatGia(double gia) {
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        return decimalFormat.format(gia);
    }

}