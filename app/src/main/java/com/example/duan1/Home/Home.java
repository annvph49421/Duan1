package com.example.duan1.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.example.duan1.GioHang.AdminOrderActivity;
import com.example.duan1.GioHang.CartActivity;
import com.example.duan1.GioHang.OrderConfirmationActivity;
import com.example.duan1.ManHinhLogin.ManHinhLogin;
import com.example.duan1.ManHinhTT.TTCaNhanFragment;
import com.example.duan1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolBar;
    NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    //Email của admin
    private List<String> adminEmails = Arrays.asList(
            "admin@gmail.com", // Email admin đầu tiên
            "superadmin@example.com" // Email admin thứ hai, nếu có
    );
    FirebaseUser user;


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
        navigationView = findViewById(R.id.navigationView);


        //annvph49421@gmail.com
        //11062005
        //An chuc nang dua tren vai tro
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            if (email != null && adminEmails.contains(email)) {
                // Người dùng là admin
                enableAdminFeatures();
            } else {
                // Người dùng là user
                disableAdminFeatures();
            }
        }



        //ánh xạ
        drawerLayout= findViewById(R.id.main);
        toolBar= findViewById(R.id.toolBar);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        if (user != null) {
            String name = user.getDisplayName();  // Tên người dùng
            String email = user.getEmail();  // Email người dùng

            // Nếu người dùng chưa cập nhật tên, ta có thể hiển thị email thay thế
            if (name == null) {
                name = email;
            }

            // Cập nhật thông tin vào Navigation Drawer
            updateNavigationHeader(name, email);
        }





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
                fragment =HomeFragment.newInstance();
            }else if (id == R.id.mDSDT){
                toolBar.setTitle("Danh sách điện thoại");
                fragment= DSDienThoaiFragment.newInstance();
            }else if(id == R.id.mQLDT){
                toolBar.setTitle("Quản lý điện thoại");
                fragment= QuanLyDTFragment.newInstance();
            } else if (id == R.id.mQLDH) {
                Intent inten = new Intent(Home.this, AdminOrderActivity.class);
                startActivity(inten);
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
                fragment = DSDienThoaiFragment.newInstance();
            }else if (id == R.id.mbgiohang) {
                // Chuyển sang màn hình giỏ hàng
                Intent intent = new Intent(Home.this, CartActivity.class);
                startActivity(intent);
                return true;
            } else if (id==R.id.mbvi) {
                Intent intent = new Intent(Home.this, OrderConfirmationActivity.class);
                startActivity(intent);

            }
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
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
        } else if (item.getItemId() == R.id.mbvi) {
            Intent intent = new Intent(Home.this, OrderConfirmationActivity.class);
            startActivity(intent);

        }else if(item.getItemId()==R.id.mQLDH){
            Intent inten = new Intent(Home.this, AdminOrderActivity.class);
            startActivity(inten);
        }



        return super.onOptionsItemSelected(item);
    }



    //Cac tinh nang cua tung vai tro
    private void enableAdminFeatures() {
        navigationView.getMenu().findItem(R.id.mQLDT).setVisible(true); // Quản lý điện thoại chỉ hiển thị cho admin
    }

    private void disableAdminFeatures() {
        navigationView.getMenu().findItem(R.id.mQLDT).setVisible(false);
        navigationView.getMenu().findItem(R.id.mQLDH).setVisible(false);
        navigationView.getMenu().findItem(R.id.mTK).setVisible(false);
        // Ẩn Quản lý điện thoại
    }



    private void updateNavigationHeader(String name, String email) {
        // Lấy NavigationView và header view
        NavigationView navigationView = findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);

        // Cập nhật tên và email vào các TextView
        TextView navHeaderName = headerView.findViewById(R.id.tvName);
        TextView navHeaderEmail = headerView.findViewById(R.id.tvMail);

        navHeaderName.setText(name);
        navHeaderEmail.setText(email);
    }
}