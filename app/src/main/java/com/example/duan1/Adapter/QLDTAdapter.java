package com.example.duan1.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DAO.QLDTDAO;
import com.example.duan1.Models.QLDT;
import com.example.duan1.R;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class QLDTAdapter extends RecyclerView.Adapter<QLDTAdapter.ViewHolder> {
    private Context context;
    private ArrayList<QLDT> list;

    private QLDTDAO qldtdao;

    public QLDTAdapter(Context context, ArrayList<QLDT> list, QLDTDAO qldtdao) {
        this.context = context;
        this.list = list;
        this.qldtdao = qldtdao;
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

        //sửa
        holder.btn_sua_qldt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdate(list.get(holder.getAdapterPosition()));
            }
        });

        //xóa
        holder.tvql_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDelete(list.get(holder.getAdapterPosition()).getTendt(),
                        list.get(holder.getAdapterPosition()).getMadt());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvql_tendt, tvql_xoa, tvql_gia, tvql_dungluong, tvql_rate, btn_sua_qldt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvql_xoa= itemView.findViewById(R.id.tvql_xoa);
            tvql_gia= itemView.findViewById(R.id.tvql_gia);
            tvql_dungluong= itemView.findViewById(R.id.tvql_dungluong);
            tvql_rate= itemView.findViewById(R.id.tvql_rate);
            tvql_tendt= itemView.findViewById(R.id.tvql_tendt);
            btn_sua_qldt= itemView.findViewById(R.id.btn_sua_qldt);
        }
    }

    public static class Utils {
        public static String formatCurrency(int amount) {
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            return formatter.format(amount) + "đ";
        }
    }

    private void showDialogUpdate(QLDT qldt){
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        LayoutInflater inflater= ((Activity)context).getLayoutInflater();
        View view= inflater.inflate(R.layout.dialog_update_qldt, null);
        builder.setView(view);

        AlertDialog alertDialog= builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edt_ten_qldtS= view.findViewById(R.id.edt_ten_qldtS);
        EditText edt_sao_qldtS= view.findViewById(R.id.edt_sao_qldtS);
        EditText edt_dunglg_qldtS= view.findViewById(R.id.edt_dunglg_qldtS);
        EditText edt_gia_qldtS= view.findViewById(R.id.edt_gia_qldtS);
        Button btn_update_qldt= view.findViewById(R.id.btn_update_qldt);
        Button btn_cancel_qldtS= view.findViewById(R.id.btn_cancel_qldtS);

        //dua du lieu len tv
        edt_ten_qldtS.setText(qldt.getTendt());
        edt_sao_qldtS.setText(qldt.getSao());
        edt_dunglg_qldtS.setText(qldt.getDungluong());
        edt_gia_qldtS.setText(String.valueOf(qldt.getGia()));

        btn_cancel_qldtS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_update_qldt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int madt= qldt.getMadt();
                String tendt= edt_ten_qldtS.getText().toString();
                String sao= edt_sao_qldtS.getText().toString();
                String dungluong= edt_dunglg_qldtS.getText().toString();
                String gia= edt_gia_qldtS.getText().toString();

                if (tendt.length() == 0 || gia.length() == 0 || sao.length() == 0 || dungluong.length() == 0){
                    Toast.makeText(context, "Vui lòng không để trống", Toast.LENGTH_SHORT).show();

                }else {
                    QLDT qldtSua= new QLDT(madt, tendt, sao, dungluong, Integer.parseInt(gia));
                     boolean check= qldtdao.suaDT(qldtSua);
                     if (check){
                         Toast.makeText(context, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();

                         list.clear();
                         list= qldtdao.getDS();
                         notifyDataSetChanged();

                         alertDialog.dismiss();

                     }else {
                         Toast.makeText(context, "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
                     }
                }
            }
        });



    }

    private void showDialogDelete(String tendt, int madt){
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa điện thoại \"" + tendt +"\" không? ");
        builder.setIcon(R.drawable.ic_warning);
        
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean check= qldtdao.xoaDT(madt);
                if (check){
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list= qldtdao.getDS();
                    notifyDataSetChanged();
                }
            }
        });

        builder.setNegativeButton("Hủy", null);

        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
}
