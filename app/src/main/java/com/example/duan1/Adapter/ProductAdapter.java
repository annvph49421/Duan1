package com.example.duan1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Models.ProductModels;
import com.example.duan1.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<ProductModels> productModels;
    public ProductAdapter(List<ProductModels> productModels){
        this.productModels = productModels;
    }
    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModels product = productModels.get(position);
        holder.AnhSanPham.setImageResource(product.getImg());
        holder.TenSanPham.setText(product.getName());
        holder.GiaSanPham.setText(product.getPrice());
    }



    @Override
    public int getItemCount() {

        return productModels.size();
    }


    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView AnhSanPham;
        TextView TenSanPham;
        TextView GiaSanPham;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            AnhSanPham = itemView.findViewById(R.id.AnhSanPham);
            TenSanPham = itemView.findViewById(R.id.TenSanPham);
            GiaSanPham = itemView.findViewById(R.id.GiaSanPham);
        }
    }
}
