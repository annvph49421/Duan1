package com.example.duan1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        ArrayList<QLDT> list= qldtdao.getDS();

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        recyclerView_qldt.setLayoutManager(linearLayoutManager);
        QLDTAdapter adapter= new QLDTAdapter(getContext(), list);
        recyclerView_qldt.setAdapter(adapter);

        return view;
    }

    public static QuanLyDTFragment newInstance() {
        QuanLyDTFragment fragment = new QuanLyDTFragment();
        return fragment;
    }
}
