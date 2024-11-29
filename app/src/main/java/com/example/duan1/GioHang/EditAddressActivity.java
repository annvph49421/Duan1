package com.example.duan1.GioHang;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1.DAO.CartDAO;
import com.example.duan1.R;

public class EditAddressActivity extends AppCompatActivity {

    private EditText editTextName, editTextAddress, editTextPhone;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        // Khởi tạo các thành phần
        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        btnSave = findViewById(R.id.btnSave);

        // Lấy thông tin địa chỉ hiện tại từ CartDAO nếu có
        CartDAO cartDAO = new CartDAO(this);
        String currentAddress = cartDAO.getAddress();
        if (currentAddress != null) {
            // Hiển thị địa chỉ hiện tại vào EditText
            editTextAddress.setText(currentAddress);
        }

        btnSave.setOnClickListener(v -> {
            // Lấy dữ liệu từ các EditText
            String name = editTextName.getText().toString();
            String address = editTextAddress.getText().toString();
            String phone = editTextPhone.getText().toString();

            // Lưu địa chỉ mới nếu các trường không rỗng
            if (!name.isEmpty() && !address.isEmpty() && !phone.isEmpty()) {
                // Lưu địa chỉ mới vào cơ sở dữ liệu
                cartDAO.addAddress(address);

                // Trả kết quả về CartActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", name);
                resultIntent.putExtra("address", address);
                resultIntent.putExtra("phone", phone);
                setResult(RESULT_OK, resultIntent);
                finish();  // Quay lại CartActivity
            } else {
                Toast.makeText(EditAddressActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }
}