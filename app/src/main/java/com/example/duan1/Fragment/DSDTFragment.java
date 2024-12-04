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

import com.example.duan1.Adapter.DSDTAdapter;
import com.example.duan1.Adapter.QLDTAdapter;
import com.example.duan1.DAO.QLDTDAO;
import com.example.duan1.Models.QLDT;
import com.example.duan1.R;

import java.util.ArrayList;

public class DSDTFragment extends Fragment {
    private RecyclerView recyclerView_dsdt;
    private QLDTDAO qldtdao;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_danh_sach_dien_thoai, container, false);

         recyclerView_dsdt= view.findViewById(R.id.recyclerView_dsdt);
         qldtdao= new QLDTDAO(getContext());
         loadData();

         return view;
    }

    private void loadData() {
        ArrayList<QLDT> list= qldtdao.getDS();

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        recyclerView_dsdt.setLayoutManager(linearLayoutManager);
        DSDTAdapter adapter= new DSDTAdapter(getContext(), list);
        recyclerView_dsdt.setAdapter(adapter);
    }

    public static DSDTFragment newInstance() {
        DSDTFragment fragment = new DSDTFragment();
        return fragment;
    }
}
