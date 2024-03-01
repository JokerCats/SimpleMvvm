package net.jkcats.simplemvvm;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import net.jkcats.simplemvvm.basics.StandardActivity;
import net.jkcats.simplemvvm.databinding.ActivityMainBinding;
import net.jkcats.simplemvvm.viewmodels.HomeViewModel;

public class MainActivity extends StandardActivity<ActivityMainBinding, HomeViewModel> {

    private Button mSendBtn;

    private TextView mContentTv;

//    @Override
//    public int setPageResID() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    protected void initViews() {
////        mSendBtn = findViewById(R.id.btn_send);
////        mContentTv = findViewById(R.id.tv_content);
//
////        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
////                this,
////        )
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mSendBtn.setOnClickListener(v -> {
//            // 请求数据
//            if (mViewModel != null) {
//                mViewModel.getHomeData();
//            }
//        });
//
//        if (mViewModel != null) {
//            mViewModel.homeData.observe(this, model -> {
//                StringBuilder builder = new StringBuilder();
//                for (HomeModel.HomeData data : model.data) {
//                    builder.append(data.title).append("\r\n");
//                }
//                String content = builder.toString();
//
//                if (content.isEmpty()) {
//                    mContentTv.setTextColor(Color.RED);
//                    mContentTv.setText(getString(R.string.app_name));
//                } else {
//                    mContentTv.setTextColor(Color.GREEN);
//                    mContentTv.setText(content);
//                }
//            });
//        }
    }
}