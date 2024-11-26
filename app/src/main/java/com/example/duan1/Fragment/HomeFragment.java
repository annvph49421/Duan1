package com.example.duan1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1.Adapter.ProductAdapter;
import com.example.duan1.ChinhSua.ItemDecoration;
import com.example.duan1.Models.ProductModels;
import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<ProductModels> productList;

    public HomeFragment () {
        // Required empty public constructor
    }



    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

        return new HomeFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        productList = new ArrayList<>();
        productList.add(new ProductModels(R.drawable.iphone15,"Iphone 15 Pro Max 256GB","Giá: 31.000.000đ"));
        productList.add(new ProductModels(R.drawable.product_oppo12_1,"OPPO Reno12 128GB","Giá: 12.290.000đ"));
        productList.add(new ProductModels(R.drawable.product_samsung_1,"Samsung Galaxy S24 Ultra 12GB","Giá: 29.990.000đ"));
        productList.add(new ProductModels(R.drawable.product_vivo_1,"Vivo V30E 256GB Limited","Giá: 9.490.000đ"));

        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);
        //Thêm khoảng cách giữa các item theo hàng doc
       // recyclerView.addItemDecoration(new ItemDecoration(4));




        super.onViewCreated(view, savedInstanceState);
    }
}