package com.example.duan1.Fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.QLDTAdapter;
import com.example.duan1.DAO.QLDTDAO;
import com.example.duan1.Models.QLDT;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QuanLyDTFragment extends Fragment {
    private RecyclerView recyclerView_qldt;
    private FloatingActionButton floatAdd_qldt;
    private QLDTDAO qldtdao;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_quanlydt, container, false);

        recyclerView_qldt= view.findViewById(R.id.recyclerView_qldt);
        floatAdd_qldt= view.findViewById(R.id.floatAdd_qldt);

        qldtdao= new QLDTDAO(getContext());
        loadData();


        floatAdd_qldt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAdd();
            }
        });

        return view;
    }

    private void showDialogAdd(){
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        LayoutInflater inflater= getLayoutInflater();
        View view= inflater.inflate(R.layout.dialog_add_qldt, null);
        builder.setView(view);

        AlertDialog alertDialog= builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edt_ten_qldt= view.findViewById(R.id.edt_ten_qldt);
        EditText edt_sao_qldt= view.findViewById(R.id.edt_sao_qldt);
        EditText edt_dunglg_qldt= view.findViewById(R.id.edt_dunglg_qldt);
        EditText edt_gia_qldt= view.findViewById(R.id.edt_gia_qldt);
        Button btn_add_qldt= view.findViewById(R.id.btn_add_qldt);
        Button btn_cancel_qldt= view.findViewById(R.id.btn_cancel_qldt);

        btn_add_qldt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tendt= edt_ten_qldt.getText().toString();
                String sao= edt_sao_qldt.getText().toString();
                String dungluong= edt_dunglg_qldt.getText().toString();
                String gia= edt_gia_qldt.getText().toString();

                if (tendt.length() == 0 || sao.length() == 0 || dungluong.length() == 0 || gia.length() == 0){
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                }else {
                    QLDT qldt= new QLDT(tendt, sao, dungluong, Integer.parseInt(gia));
                    boolean check= qldtdao.themDTMoi(qldt);
                    if (check){
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btn_cancel_qldt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public static QuanLyDTFragment newInstance() {
        QuanLyDTFragment fragment = new QuanLyDTFragment();
        return fragment;
    }

    private void loadData(){
        ArrayList<QLDT> list= qldtdao.getDS();

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        recyclerView_qldt.setLayoutManager(linearLayoutManager);
        QLDTAdapter adapter= new QLDTAdapter(getContext(), list, qldtdao);
        recyclerView_qldt.setAdapter(adapter);
    }
}
