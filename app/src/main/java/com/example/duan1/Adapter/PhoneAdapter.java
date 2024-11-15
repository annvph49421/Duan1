package com.example.duan1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Models.PhoneModels;
import com.example.duan1.R;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {

    private List<PhoneModels> phoneModelsList;

    public PhoneAdapter(List<PhoneModels> phoneModelsList) {
        this.phoneModelsList = phoneModelsList;
    }

    @NonNull
    @Override
    public PhoneAdapter.PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone,parent,false);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        PhoneModels phone = phoneModelsList.get(position);
        holder.tvNamePhone.setText(phone.getName());
        holder.tvRating.setText(String.valueOf(phone.getRating()) + "★");
        holder.tvPrice.setText(phone.getPrice() + "VNĐ");
        holder.imgPhone.setImageResource(phone.getImage());
    }



    @Override
    public int getItemCount() {
        return phoneModelsList.size();
    }

    public static class PhoneViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhone;
        TextView tvNamePhone,tvRating,tvPrice;

        public PhoneViewHolder(@NonNull View itemView) {

            super(itemView);
            imgPhone = itemView.findViewById(R.id.AnhDienThoai);
            tvNamePhone = itemView.findViewById(R.id.tvTenDienThoai);
            tvRating = itemView.findViewById(R.id.tvPhoneRating);
            tvPrice = itemView.findViewById(R.id.tvGiaDienThoai);
        }
    }
}
