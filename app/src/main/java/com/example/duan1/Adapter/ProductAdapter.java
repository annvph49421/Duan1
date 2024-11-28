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
import com.example.duan1.Models.ProductModels;
import com.example.duan1.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductModels> productModels;
    public ProductAdapter(Context context, List<ProductModels> productModels){
        this.productModels = productModels;
        this.context = context;
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
        holder.TenSanPham.setText(product.getName());
        holder.GiaSanPham.setText(product.getPrice());
        holder.AnhSanPham.setImageResource(product.getImg());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ctsp_all.class); // Màn hình chi tiết sản phẩm
            // Truyền dữ liệu sản phẩm qua Intent
            intent.putExtra("name", product.getName());
            intent.putExtra(("rating"), String.valueOf(product.getRating()) + "★");
            intent.putExtra("moTa", product.getMoTa());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("imageUrl", product.getImg());
            context.startActivity(intent);
        });
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
