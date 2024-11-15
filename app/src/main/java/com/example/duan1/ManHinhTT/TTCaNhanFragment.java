package com.example.duan1.ManHinhTT;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_t_t_ca_nhan, container, false);
    }
}