package com.example.duan1.Adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Models.MenuItem;
import com.example.duan1.R;

import java.util.List;

public class Adapter_TTCaNhan extends RecyclerView.Adapter<Adapter_TTCaNhan.MenuViewHolder> {
    private final List<MenuItem> menuItems;

    public Adapter_TTCaNhan(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public Adapter_TTCaNhan.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ttcn,parent,false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
            MenuItem menuItem = menuItems.get(position);
            holder.icon.setImageResource(menuItem.getIcon());
            holder.title.setText(menuItem.getTitle());
    }


    @Override
    public int getItemCount() {
        return menuItems.size();
    }


    public static class MenuViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView title;


        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.menuTitle);
        }
    }
}
