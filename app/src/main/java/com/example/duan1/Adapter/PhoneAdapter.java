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

import com.example.duan1.ChiTietSP.ctsp_all;
import com.example.duan1.ChiTietSP.ctsp_ip; // Màn hình chi tiết sản phẩm
import com.example.duan1.Models.PhoneModels;
import com.example.duan1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {

    private List<PhoneModels> phoneModelsList;
    private Context context;

    public PhoneAdapter(Context context, List<PhoneModels> phoneModelsList) {
        this.context = context; // Sửa lỗi gán context
        this.phoneModelsList = phoneModelsList;
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone, parent, false);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        PhoneModels phone = phoneModelsList.get(position);
        holder.tvNamePhone.setText(phone.getName());
        holder.tvRating.setText(String.valueOf(phone.getRating()) + "★");
        holder.tvPrice.setText(phone.getPrice());
        holder.imgPhone.setImageResource(phone.getImage());

        // Xử lý click vào từng item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ctsp_all.class); // Màn hình chi tiết sản phẩm
            // Truyền dữ liệu sản phẩm qua Intent
            intent.putExtra("name", phone.getName());
            intent.putExtra(("rating"), String.valueOf(phone.getRating()) + "★");
            intent.putExtra("price", phone.getPrice());
            intent.putExtra("imageUrl", phone.getImage());
            context.startActivity(intent);
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
