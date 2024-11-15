package com.example.duan1.Home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.duan1.ManHinhTT.TTCaNhanFragment;
import com.example.duan1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolBar;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
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

        //ánh xạ
        drawerLayout= findViewById(R.id.main);
        toolBar= findViewById(R.id.toolBar);
        navigationView= findViewById(R.id.navigationView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        setSupportActionBar(toolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, 0, 0);
        toggle.setDrawerIndicatorEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        toggle.syncState();

        drawerLayout.addDrawerListener(toggle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, TTCaNhanFragment.newInstance()).commit();
        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            int id= item.getItemId();
            if(id == R.id.mDSDT){
                toolBar.setTitle("Thong tin ca nhan");
                fragment = TTCaNhanFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();

            }
            drawerLayout.closeDrawer(GravityCompat.START); // Đóng Drawer sau khi chọn
            return true;

        });





        //setup toolbar
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);


    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }




}