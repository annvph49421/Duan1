package com.example.duan1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.ChiTietSP.ctsp_oppo; // Màn hình chi tiết sản phẩm
import com.example.duan1.Models.PhoneModels;
import com.example.duan1.R;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {

    private List<PhoneModels> phoneModelsList;
    private Context context; // Thêm context để mở màn hình chi tiết

    public PhoneAdapter(List<PhoneModels> phoneModelsList) {
        this.context = context;
        this.phoneModelsList = phoneModelsList;
    }

    @NonNull
    @Override
    public PhoneAdapter.PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone, parent, false);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        PhoneModels phone = phoneModelsList.get(position);
        holder.tvNamePhone.setText(phone.getName());
        holder.tvRating.setText(String.valueOf(phone.getRating()) + "★");
        holder.tvPrice.setText(phone.getPrice() + "VNĐ");
        holder.imgPhone.setImageResource(phone.getImage());

        // Thiết lập sự kiện click cho mỗi sản phẩm
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình chi tiết sản phẩm và truyền dữ liệu
                Intent intent = new Intent(context, ctsp_oppo.class); // Màn hình chi tiết
                // Truyền thông tin sản phẩm vào Intent
                intent.putExtra("product_name", phone.getName());
                intent.putExtra("product_price", phone.getPrice());
                intent.putExtra("product_image", phone.getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneModelsList.size();
    }

    public static class PhoneViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhone;
        TextView tvNamePhone, tvRating, tvPrice;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhone = itemView.findViewById(R.id.AnhDienThoai);
            tvNamePhone = itemView.findViewById(R.id.tvTenDienThoai);
            tvRating = itemView.findViewById(R.id.tvPhoneRating);
            tvPrice = itemView.findViewById(R.id.tvGiaDienThoai);
        }
    }
}
