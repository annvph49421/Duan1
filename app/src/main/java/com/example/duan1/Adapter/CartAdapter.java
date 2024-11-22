package com.example.duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duan1.DAO.CartDAO;
import com.example.duan1.GioHang.CartActivity;
import com.example.duan1.Models.CartItem;
import com.example.duan1.R;

import java.util.List;

public class CartAdapter extends ArrayAdapter<CartItem> {
    private List<CartItem> cartItems;
    private CartDAO cartDAO;
    private CartActivity cartActivity;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        super(context, 0, cartItems);
        this.cartItems = cartItems;
        this.cartDAO = new CartDAO(context);
        this.cartActivity = (CartActivity) context;  // Lấy CartActivity để gọi updateTotalPrice
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_item, parent, false);
        }

        final CartItem item = getItem(position);

        TextView productName = convertView.findViewById(R.id.productName);
        TextView quantity = convertView.findViewById(R.id.quantity);
        TextView price = convertView.findViewById(R.id.price);
        ImageView productImage = convertView.findViewById(R.id.productImage);
        ImageView increaseQuantity = convertView.findViewById(R.id.increaseQuantity);
        ImageView decreaseQuantity = convertView.findViewById(R.id.decreaseQuantity);
        ImageView removeProduct = convertView.findViewById(R.id.removeProduct);

        productName.setText(item.getProductName());
        quantity.setText("Số lượng: " + item.getQuantity());
        price.setText("Giá: " + item.getTotalPrice() + "đ");
        productImage.setImageResource(item.getImageResId());

        // Tăng số lượng
        increaseQuantity.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            quantity.setText("Số lượng: " + item.getQuantity());
            price.setText("Giá: " + item.getTotalPrice() + "đ");

            // Cập nhật lại trong cơ sở dữ liệu
            cartDAO.updateQuantity(item);
            cartActivity.updateTotalPrice();  // Cập nhật lại tổng giá trị giỏ hàng
            Toast.makeText(getContext(), "Đã tăng số lượng", Toast.LENGTH_SHORT).show();
        });

        // Giảm số lượng
        decreaseQuantity.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                quantity.setText("Số lượng: " + item.getQuantity());
                price.setText("Giá: " + item.getTotalPrice() + "đ");

                // Cập nhật lại trong cơ sở dữ liệu
                cartDAO.updateQuantity(item);
                cartActivity.updateTotalPrice();  // Cập nhật lại tổng giá trị giỏ hàng
                Toast.makeText(getContext(), "Đã giảm số lượng", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Số lượng không thể nhỏ hơn 1", Toast.LENGTH_SHORT).show();
            }
        });

        // Xóa sản phẩm
        removeProduct.setOnClickListener(v -> {
            cartDAO.removeItem(item);  // Xóa sản phẩm trong giỏ hàng
            cartItems.remove(position); // Xóa sản phẩm khỏi danh sách hiển thị
            notifyDataSetChanged(); // Cập nhật lại ListView
            cartActivity.updateTotalPrice();  // Cập nhật lại tổng giá trị giỏ hàng
            Toast.makeText(getContext(), "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
