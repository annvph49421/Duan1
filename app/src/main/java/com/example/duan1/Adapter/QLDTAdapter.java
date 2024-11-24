package com.example.duan1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Models.QLDT;
import com.example.duan1.R;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class QLDTAdapter extends RecyclerView.Adapter<QLDTAdapter.ViewHolder> {
    private Context context;
    private ArrayList<QLDT> list;

    public QLDTAdapter(Context context, ArrayList<QLDT> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= ((Activity)context).getLayoutInflater();
        View view= inflater.inflate(R.layout.item_qldt, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvql_tendt.setText(list.get(position).getTendt());
        holder.tvql_rate.setText(String.valueOf(list.get(position).getSao()));
        holder.tvql_dungluong.setText(list.get(position).getDungluong());
        holder.tvql_gia.setText(String.valueOf(Utils.formatCurrency(list.get(position).getGia())));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvql_tendt, tvql_xoa, tvql_gia, tvql_dungluong, tvql_rate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvql_xoa= itemView.findViewById(R.id.tvql_xoa);
            tvql_gia= itemView.findViewById(R.id.tvql_gia);
            tvql_dungluong= itemView.findViewById(R.id.tvql_dungluong);
            tvql_rate= itemView.findViewById(R.id.tvql_rate);
            tvql_tendt= itemView.findViewById(R.id.tvql_tendt);
        }
    }

    public static class Utils {
        public static String formatCurrency(int amount) {
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            return formatter.format(amount) + "đ";
        }
    }
}
