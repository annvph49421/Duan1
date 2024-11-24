package com.example.duan1.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.duan1.Fragment.DSDienThoaiFragment;
import com.example.duan1.Fragment.HomeFragment;

import com.example.duan1.Fragment.QuanLyDTFragment;
import com.example.duan1.GioHang.CartActivity;
import com.example.duan1.ManHinhTT.TTCaNhanFragment;
import com.example.duan1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolBar;
    NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //annvph49421@gmail.com
        //11062005

        //ánh xạ
        drawerLayout= findViewById(R.id.main);
        toolBar= findViewById(R.id.toolBar);
        navigationView = findViewById(R.id.navigationView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        setSupportActionBar(toolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, 0, 0);
        toggle.setDrawerIndicatorEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();
            if (id == R.id.mHome) {
                toolBar.setTitle("Trang chủ");
                fragment =TTCaNhanFragment.newInstance();
            }else if (id == R.id.mDSDT){
                toolBar.setTitle("Danh sách điện thoại");
                fragment= DSDienThoaiFragment.newInstance();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            drawerLayout.closeDrawers();
            return true;
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();
            if (id == R.id.mbuser) {
                toolBar.setTitle("Thông tin cá nhân");
                fragment =TTCaNhanFragment.newInstance();
            } else if (id == R.id.mbHome) {
                {
                toolBar.setTitle("Trang chủ");
                fragment = HomeFragment.newInstance();
                }
            } else if (id == R.id.mbphone) {
                toolBar.setTitle("Danh sách điện thoại");
                fragment = QuanLyDTFragment.newInstance();
            }else if (id == R.id.mbgiohang) {
                // Chuyển sang màn hình giỏ hàng
                Intent intent = new Intent(Home.this, CartActivity.class);
                startActivity(intent);
                return true;
            }


            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            drawerLayout.closeDrawers();
            return true;
        });


    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigationbottom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.mbgiohang) {
            // Chuyển sang màn hình giỏ hàng
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }








}