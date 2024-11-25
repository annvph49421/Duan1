package com.example.duan1.ManHinhTT;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duan1.ManHinhLogin.ManHinhLogin;
import com.example.duan1.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TTCaNhanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TTCaNhanFragment extends Fragment {


    public TTCaNhanFragment() {
        // Required empty public constructor
    }


    public static TTCaNhanFragment newInstance() {
        TTCaNhanFragment fragment = new TTCaNhanFragment();
        return new TTCaNhanFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_t_t_ca_nhan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView tvLogout = view.findViewById(R.id.tvLogout);
        TextView tvTTCN = view.findViewById(R.id.tvTTCN);

        //Thông tin cá nhân
        tvTTCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TTCaNhan2.class);
                startActivity(intent);
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),  ManHinhLogin.class);
                startActivity(intent);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}