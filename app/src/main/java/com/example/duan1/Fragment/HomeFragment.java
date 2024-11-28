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
        productList.add(new ProductModels(R.drawable.iphone15,"Iphone 15 Pro Max 256GB","31.000.000đ","iPhone 15 Pro Max sở hữu màn hình Super Retina XDR OLED 6.7 inches với độ phân giải 2796 x 1290 pixels, cung cấp trải nghiệm hình ảnh sắc nét, chân thực. So với các phiên bản tiền nhiệm, thế hệ iPhone 15 bản Pro Max đảm bảo mang tới hiệu năng mạnh mẽ với sự hỗ trợ của chipset Apple A17 Pro, cùng bộ nhớ ấn tượng. Đặc biệt hơn, điện thoại iPhone 15 ProMax mới này còn được đánh giá cao với camera sau 48MP và camera trước 12MP, hỗ trợ chụp ảnh với độ rõ nét cực đỉnh.","4.9★"));
        productList.add(new ProductModels(R.drawable.product_oppo12_1,"OPPO Reno12 128GB","12.290.000đ","OPPO Reno12 5G với sự hỗ trợ của chip xử lý Chip MediaTek 7300-Energy kết hợp với 12GB RAM cung cấp hiệu năng mượt mà và xử lý đa nhiệm hiệu quả. Sở hữu màn hình 6.7 inches AMOLED 120Hz đã giúp Reno 12 có sức hút hơn khi lướt web kèm pin trâu 50000mAh, cho phép duy trì xử lý công việc xuyên suốt ngày dài.","4.9★"));
        productList.add(new ProductModels(R.drawable.product_samsung_1,"Samsung Galaxy S24 Ultra 12GB","29.990.000đ","Samsung S24 Ultra là siêu phẩm smartphone đỉnh cao mở đầu năm 2024 đến từ nhà Samsung với chip Snapdragon 8 Gen 3 For Galaxy mạnh mẽ, công nghệ tương lai Galaxy AI cùng khung viền Titan đẳng cấp hứa hẹn sẽ mang tới nhiều sự thay đổi lớn về mặt thiết kế và cấu hình. SS Galaxy S24 bản Ultra sở hữu màn hình 6.8 inch Dynamic AMOLED 2X tần số quét 120Hz. Máy cũng sở hữu camera chính 200MP, camera zoom quang học 50MP, camera tele 10MP và camera góc siêu rộng 12MP","4.9★"));
        productList.add(new ProductModels(R.drawable.product_vivo_1,"Vivo V30E 256GB Limited","9.490.000đ","Điện thoại vivo V30E mạnh mẽ với chipset Snapdragon 6 Gen 1, kết hợp với 12GB RAM và 256GB bộ nhớ trong, đảm bảo hiệu suất ấn tượng cho mọi tác vụ. Màn hình AMOLED 6.68 inch của máy cung cấp trải nghiệm hình ảnh sắc nét đi kèm cụm camera kép ở mặt sau với camera chính lên đến 50MP. Vivo V30E còn sở hữu pin dung lượng 5.500 mAh, hỗ trợ sạc nhanh 44W, tối ưu hóa trải nghiệm sử dụng hàng ngày.","4.9★"));

        productAdapter = new ProductAdapter(getContext(),productList);
        recyclerView.setAdapter(productAdapter);
        //Thêm khoảng cách giữa các item theo hàng doc
       // recyclerView.addItemDecoration(new ItemDecoration(4));




        super.onViewCreated(view, savedInstanceState);
    }
}