package com.example.duan1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.duan1.Models.QLDT;
import com.example.duan1.R;

import java.util.ArrayList;

public class DSDTAdapter extends RecyclerView.Adapter<DSDTAdapter.ViewHolder>{

    private Context context;
    private ArrayList<QLDT> list;

    public DSDTAdapter(Context context, ArrayList<QLDT> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= ((Activity)context).getLayoutInflater();
        View view= inflater.inflate(R.layout.item_dsdt, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QLDT currentQLDT= list.get(position);

        holder.tvds_tendt.setText(list.get(position).getTendt());
        holder.tvds_rate.setText(String.valueOf(list.get(position).getSao()));
        holder.tvds_dungluong.setText(list.get(position).getDungluong());
        holder.tvds_gia.setText(String.valueOf(QLDTAdapter.Utils.formatCurrency(list.get(position).getGia())));

        String tendtimg= currentQLDT.getTendt();
        if (tendtimg.toLowerCase().contains("iphone")){
            Glide.with(context)
                    .load(R.drawable.product_ip15promax_1)
                    .error(R.drawable.error_img)
                    .into(holder.img_ds_dt);

        }else if (tendtimg.toLowerCase().contains("samsung")){
            Glide.with(context)
                    .load(R.drawable.product_samsung_1)
                    .error(R.drawable.error_img)
                    .into(holder.img_ds_dt);


        }else if (tendtimg.toLowerCase().contains("oppo")){
            Glide.with(context)
                    .load(R.drawable.product_oppo12_1)
                    .error(R.drawable.error_img)
                    .into(holder.img_ds_dt);


        }else if (tendtimg.toLowerCase().contains("vivo")){
            Glide.with(context)
                    .load(R.drawable.product_vivo_1)
                    .error(R.drawable.error_img)
                    .into(holder.img_ds_dt);

        }else {
            Glide.with(context)
                    .load(R.drawable.error_img)
                    .error(R.drawable.error_img)
                    .into(holder.img_ds_dt);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvds_tendt, tvds_gia, tvds_dungluong, tvds_rate;
        ImageView img_ds_dt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tvds_gia= itemView.findViewById(R.id.tvds_gia);
            tvds_dungluong= itemView.findViewById(R.id.tvds_dungluong);
            tvds_rate= itemView.findViewById(R.id.tvds_rate);
            tvds_tendt= itemView.findViewById(R.id.tvds_tendt);
            img_ds_dt= itemView.findViewById(R.id.img_ds_dt);
        }
    }
}
