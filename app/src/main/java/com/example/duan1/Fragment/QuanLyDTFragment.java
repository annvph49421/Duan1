package com.example.duan1.Fragment;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
    private static final int REQUEST_CODE_PICK_IMAGE = 1001;


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
        ImageView btn_addimg_qldt= view.findViewById(R.id.btn_addimg_qldt);


        btn_add_qldt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tendt= edt_ten_qldt.getText().toString();
                String sao= edt_sao_qldt.getText().toString();
                String dungluong= edt_dunglg_qldt.getText().toString();
                String gia= edt_gia_qldt.getText().toString();
                Uri imgUri= (Uri) btn_addimg_qldt.getTag();
                String image= imgUri != null ? imgUri.toString() : null;

                if (tendt.length() == 0 || sao.length() == 0 || dungluong.length() == 0 || gia.length() == 0){
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                }else {
                    QLDT qldt= new QLDT(tendt, sao, dungluong, Integer.parseInt(gia),image);
                    boolean check= qldtdao.themDTMoi(qldt, imgUri);
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

        btn_addimg_qldt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PICK_IMAGE);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
                }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            Uri selectedImageUri= data.getData();
            Log.d("URI", "Selected Image URI: " + selectedImageUri);

            if (getView() != null){
                ImageView btn_addimg_qldt= getView().findViewById(R.id.btn_addimg_qldt);
                ImageView img_addimg_qldt= getView().findViewById(R.id.img_addimg_qldt);
                if (img_addimg_qldt != null){
                    Glide.with(this).load(selectedImageUri).error(R.drawable.error_img).into(img_addimg_qldt);

                }else {
                    Log.e("Error", "ImageView btn_addimg_qldt is null");
                }
            }else {
                Log.e("Error", "Fragment's view is null");
            }
        }
    }
}
