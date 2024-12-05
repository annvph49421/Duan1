package com.example.duan1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.SQLite.TopPhone;

import java.util.List;

// TopPhonesAdapter.java
public class TopPhonesAdapter extends RecyclerView.Adapter<TopPhonesAdapter.TopPhoneViewHolder> {
    private List<TopPhone> topPhoneList;

    public TopPhonesAdapter(List<TopPhone> topPhoneList) {
        this.topPhoneList = topPhoneList;
    }

    @NonNull
    @Override
    public TopPhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_phone, parent, false);
        return new TopPhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPhoneViewHolder holder, int position) {
        TopPhone topPhone = topPhoneList.get(position);
        holder.productName.setText(topPhone.getProductName());
        holder.soldQuantity.setText(String.valueOf(topPhone.getSoldQuantity()));
    }

    @Override
    public int getItemCount() {
        return topPhoneList.size();
    }

    public static class TopPhoneViewHolder extends RecyclerView.ViewHolder {
        TextView productName, soldQuantity;

        public TopPhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            soldQuantity = itemView.findViewById(R.id.soldQuantity);
        }
    }
}

