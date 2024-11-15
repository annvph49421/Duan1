package com.example.duan1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1.Adapter.PhoneAdapter;
import com.example.duan1.Models.PhoneModels;
import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

public class DSDienThoaiFragment extends Fragment {
    private RecyclerView PhoneRecycleView;
    private PhoneAdapter phoneAdapter;
    private List<PhoneModels> phoneModelsList;

    public DSDienThoaiFragment() {
        // Required empty public constructor
    }


    public static DSDienThoaiFragment newInstance() {
        DSDienThoaiFragment fragment = new DSDienThoaiFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d_s_dien_thoai, container, false);

        PhoneRecycleView = view.findViewById(R.id.PhoneRecycleView);
        PhoneRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        phoneModelsList = getListPhone();
        phoneAdapter = new PhoneAdapter(phoneModelsList);
        PhoneRecycleView.setAdapter(phoneAdapter);

        return view;


    }

    private List<PhoneModels> getListPhone(){
        List<PhoneModels> phoneModelsList = new ArrayList<>();
        phoneModelsList.add(new PhoneModels(R.drawable.bg_ip15,"Iphone 15",4.7,"16.000.000 VNĐ"));
        phoneModelsList.add(new PhoneModels(R.drawable.bg_ip15,"Iphone 15",4.7,"16.000.000 VNĐ"));
        phoneModelsList.add(new PhoneModels(R.drawable.bg_ip15,"Iphone 15",4.7,"16.000.000 VNĐ"));
        phoneModelsList.add(new PhoneModels(R.drawable.bg_ip15,"Iphone 15",4.7,"16.000.000 VNĐ"));
        return phoneModelsList;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }
}