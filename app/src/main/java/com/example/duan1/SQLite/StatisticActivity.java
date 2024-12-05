package com.example.duan1.SQLite;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.TopPhonesAdapter;
import com.example.duan1.DAO.OrderDAO;
import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

// StatisticActivity.java
public class StatisticActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TopPhonesAdapter adapter;
    private OrderDAO orderDAO;
    private RadioGroup timePeriodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        recyclerView = findViewById(R.id.recyclerViewTopPhones);
        timePeriodGroup = findViewById(R.id.timePeriodGroup);
        orderDAO = new OrderDAO(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        timePeriodGroup.setOnCheckedChangeListener((group, checkedId) -> {
            List<TopPhone> topPhones = new ArrayList<>();
            if (checkedId == R.id.radioToday) {
                topPhones = orderDAO.getTopPhonesByPeriod("today");
            } else if (checkedId == R.id.radioThisMonth) {
                topPhones = orderDAO.getTopPhonesByPeriod("month");
            }
            adapter = new TopPhonesAdapter(topPhones);
            recyclerView.setAdapter(adapter);
        });

        // Mặc định hiển thị thống kê cho ngày hôm nay
        timePeriodGroup.check(R.id.radioToday);
    }
}

