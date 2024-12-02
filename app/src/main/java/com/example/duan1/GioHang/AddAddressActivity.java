

package com.example.duan1.GioHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class AddAddressActivity extends AppCompatActivity {
    private EditText edtName, edtAddress, edtPhone;
    private Button btnSaveAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        btnSaveAddress = findViewById(R.id.btnSaveAddress);

        btnSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ các EditText
                String name = edtName.getText().toString();
                String address = edtAddress.getText().toString();
                String phone = edtPhone.getText().toString();


                if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(AddAddressActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    // Trả lại dữ liệu cho CartActivity
                    Intent intent = new Intent();
                    intent.putExtra("name", name);
                    intent.putExtra("address", address);
                    intent.putExtra("phone", phone);
                    setResult(RESULT_OK, intent);  // Trả kết quả cho CartActivity
                    finish();
                }
            }
        });
    }
}
