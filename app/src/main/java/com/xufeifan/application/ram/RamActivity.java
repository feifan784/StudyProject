package com.xufeifan.application.ram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.xufeifan.application.R;

public class RamActivity extends AppCompatActivity {

    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ram);

        initView();
    }

    private void initView() {
        tvShow = findViewById(R.id.tvShow);

        StringBuffer stringBuffer = new StringBuffer();

        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        //以M为单位
        int memClass = activityManager.getMemoryClass();
        int largrClass = activityManager.getLargeMemoryClass();

        stringBuffer.append("memClass:  " + memClass);
        stringBuffer.append("largeClass:" + largrClass);

        tvShow.setText(stringBuffer.toString());


    }

    /**
     * 在进程后台时候，内存不够被GC的时候，系统回通知Activity回调的方法
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
