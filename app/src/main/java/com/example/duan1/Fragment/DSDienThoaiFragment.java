package com.example.duan1.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.duan1.Adapter.PhoneAdapter;
import com.example.duan1.Models.PhoneModels;
import com.example.duan1.R;
import com.example.duan1.SQLite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DSDienThoaiFragment extends Fragment {
    private RecyclerView PhoneRecycleView;
    private PhoneAdapter phoneAdapter;
    private List<PhoneModels> phoneModelsList;
    private SearchView searchView;

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
        searchView = view.findViewById(R.id.SearchView);
        phoneAdapter = new PhoneAdapter(getContext(),phoneModelsList);
        PhoneRecycleView.setAdapter(phoneAdapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                phoneAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                phoneAdapter.getFilter().filter(s);
                return false;
            }
        });

        return view;


    }



    private List<PhoneModels> getListPhone(){
        List<PhoneModels> phoneModelsList = new ArrayList<>();
        phoneModelsList.add(new PhoneModels(R.drawable.bg_ip15,"Iphone 15 Pro Max 256GB",5,"29.490.000đ","iPhone 15 Pro Max sở hữu màn hình Super Retina XDR OLED 6.7 inches với độ phân giải 2796 x 1290 pixels, cung cấp trải nghiệm hình ảnh sắc nét, chân thực. So với các phiên bản tiền nhiệm, thế hệ iPhone 15 bản Pro Max đảm bảo mang tới hiệu năng mạnh mẽ với sự hỗ trợ của chipset Apple A17 Pro, cùng bộ nhớ ấn tượng. Đặc biệt hơn, điện thoại iPhone 15 ProMax mới này còn được đánh giá cao với camera sau 48MP và camera trước 12MP, hỗ trợ chụp ảnh với độ rõ nét cực đỉnh."));
        phoneModelsList.add(new PhoneModels(R.drawable.product_oppo12_1,"OPPO Reno12 256GB",4.7,"12.290.000đ","OPPO Reno12 5G với sự hỗ trợ của chip xử lý Chip MediaTek 7300-Energy kết hợp với 12GB RAM cung cấp hiệu năng mượt mà và xử lý đa nhiệm hiệu quả. Sở hữu màn hình 6.7 inches AMOLED 120Hz đã giúp Reno 12 có sức hút hơn khi lướt web kèm pin trâu 50000mAh, cho phép duy trì xử lý công việc xuyên suốt ngày dài."));
        phoneModelsList.add(new PhoneModels(R.drawable.product_samsung_1,"Samsung Galaxy S24 Ultra",5.0,"29.990.000đ","Samsung S24 Ultra là siêu phẩm smartphone đỉnh cao mở đầu năm 2024 đến từ nhà Samsung với chip Snapdragon 8 Gen 3 For Galaxy mạnh mẽ, công nghệ tương lai Galaxy AI cùng khung viền Titan đẳng cấp hứa hẹn sẽ mang tới nhiều sự thay đổi lớn về mặt thiết kế và cấu hình. SS Galaxy S24 bản Ultra sở hữu màn hình 6.8 inch Dynamic AMOLED 2X tần số quét 120Hz. Máy cũng sở hữu camera chính 200MP, camera zoom quang học 50MP, camera tele 10MP và camera góc siêu rộng 12MP."));
        phoneModelsList.add(new PhoneModels(R.drawable.product_vivo_1,"Vivo V30E 256GB",4.5,"9.490.000đ","Điện thoại vivo V30E mạnh mẽ với chipset Snapdragon 6 Gen 1, kết hợp với 12GB RAM và 256GB bộ nhớ trong, đảm bảo hiệu suất ấn tượng cho mọi tác vụ. Màn hình AMOLED 6.68 inch của máy cung cấp trải nghiệm hình ảnh sắc nét đi kèm cụm camera kép ở mặt sau với camera chính lên đến 50MP. Vivo V30E còn sở hữu pin dung lượng 5.500 mAh, hỗ trợ sạc nhanh 44W, tối ưu hóa trải nghiệm sử dụng hàng ngày.")) ;
        phoneModelsList.add(new PhoneModels(R.drawable.bg_iphone14,"Redmin note 12 Turbo",5,"29.490.000đ","Xiaomi Redmi Note 12 Turbor 8GB 128GB tỏa sáng với diện mạo viền vuông cực thời thượng cùng hiệu suất mạnh mẽ nhờ sở hữu con chip Snapdragon 685 ấn tượng. Chất lượng hiển thị hình ảnh của Redmi Note 12 Vàng cũng khá sắc nét thông qua tấm nền AMOLED 120Hz hiện đại. Chưa hết, máy còn sở hữu cụm 3 camera với độ rõ nét lên tới 50MP cùng viên pin 5000mAh và s ạc nhanh 33W giúp đáp ứng được mọi nhu cầu sử dụng của người dùng."));

        return phoneModelsList;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }
}