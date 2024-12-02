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

import java.util.ArrayList;
import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {

    private List<PhoneModels> phoneModelsList;
    private List<PhoneModels> phoneModelsListFull;
    private Context context;

    public PhoneAdapter(Context context, List<PhoneModels> phoneModelsList) {
        this.context = context; // Sửa lỗi gán context
        this.phoneModelsList = phoneModelsList;
        this.phoneModelsListFull = new ArrayList<>(phoneModelsList);
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
            intent.putExtra("moTa", phone.getMoTa());
            intent.putExtra("price", phone.getPrice());
            intent.putExtra("imageUrl", phone.getImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return phoneModelsList.size();
    }

    public android.widget.Filter getFilter(){
        return filter;
    }

    private final android.widget.Filter filter = new android.widget.Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PhoneModels> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(phoneModelsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (PhoneModels phone : phoneModelsListFull) {
                    if (phone.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(phone);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            phoneModelsList.clear();
            phoneModelsList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };


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
