package io.qthjen_dev.deviceinfo.Activity;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import io.qthjen_dev.deviceinfo.Adapter.TabLayoutAdapter;
import io.qthjen_dev.deviceinfo.R;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;

    private TabLayoutAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitView();

        mAdapter = new TabLayoutAdapter(getSupportFragmentManager(), getMyResource(R.string.system)
                , getMyResource(R.string.cpu), getMyResource(R.string.device)
                , getMyResource(R.string.memory), getMyResource(R.string.bettery)
                , getMyResource(R.string.sensor), getMyResource(R.string.about));

        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        // pstree
        // dumpsys meminfo
        // top -n 1 -d 1
        // cat /proc/cpuinfo
        // cat /proc/version
        // cat /proc/meminfo
        // free
        // du (ước tính sử dụng không gian thư mục)
        // arp (Liệt kê địa chỉ IP và MAC trong mạng)
        // netstat (Liệt kê các kết nối của mục tiêu)
        // top (Danh sách các quá trình đang chạy trên hệ thống)

    }

    private String getMyResource(int mystring) {

        return getResources().getString(mystring);
    }

    private void InitView() {

        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tab_layout_main);
        mToolbar = findViewById(R.id.tbar_main);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

    }

}
