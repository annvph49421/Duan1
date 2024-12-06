package com.example.duan1.Fragment;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.duan1.Adapter.QLDTAdapter;
import com.example.duan1.DAO.QLDTDAO;
import com.example.duan1.Models.QLDT;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuanLyDTFragment extends Fragment {
    private RecyclerView recyclerView_qldt;
    private FloatingActionButton floatAdd_qldt;
    private QLDTDAO qldtdao;
    private static final int REQUEST_CODE_PICK_IMAGE = 1;

    private static final int REQUEST_CODE_PERMISSION = 100;
    private ImageView btn_addimg_qldt;


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

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        }

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
        btn_addimg_qldt= view.findViewById(R.id.btn_addimg_qldt);




        btn_add_qldt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tendt= edt_ten_qldt.getText().toString();
                String sao= edt_sao_qldt.getText().toString();
                String dungluong= edt_dunglg_qldt.getText().toString();
                String gia= edt_gia_qldt.getText().toString();
                Uri imgUri= (Uri) btn_addimg_qldt.getTag();


                if (tendt.length() == 0 || sao.length() == 0 || dungluong.length() == 0 || gia.length() == 0 || imgUri == null){
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                }else {
                    QLDT qldt= new QLDT(tendt, sao, dungluong, Integer.parseInt(gia),imgUri != null ? imgUri.toString() : null);
                    boolean check= qldtdao.themDTMoi(qldt, imgUri);
                    if (check){
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        Log.d("Debug", "Name: " + tendt);
                        Log.d("Debug", "Star: " + sao);
                        Log.d("Debug", "Storage: " + dungluong);
                        Log.d("Debug", "Price: " + gia);
                        Log.d("Debug", "Image URI: " + (imgUri != null ? imgUri.toString() : "null"));

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data.getData() != null){
            Uri selectedImageUri= data.getData();

            Log.d("URI", "Selected Image URI: " + selectedImageUri);

            try {
                if (btn_addimg_qldt != null){
                    btn_addimg_qldt.setTag(selectedImageUri);
                    Glide.with(requireContext())
                            .load(selectedImageUri)
                            .error(R.drawable.error_img)
                            .into(btn_addimg_qldt);
                }
            } catch (Exception e) {
                Log.e("Error", "Failed to load image ", e);
            }

        }
    }




    private void loadData(){
        ArrayList<QLDT> list= qldtdao.getDS();

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        recyclerView_qldt.setLayoutManager(linearLayoutManager);
        QLDTAdapter adapter= new QLDTAdapter(getContext(), list, qldtdao);
        recyclerView_qldt.setAdapter(adapter);
    }

    public static QuanLyDTFragment newInstance() {
        QuanLyDTFragment fragment = new QuanLyDTFragment();
        return fragment;
    }




}
