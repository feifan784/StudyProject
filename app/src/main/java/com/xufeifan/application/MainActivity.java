package com.xufeifan.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xufeifan.application.databinding.DataBindingActivity;
import com.xufeifan.application.dispatch.DispatchActivity;
import com.xufeifan.application.drag.Channel;
import com.xufeifan.application.drawer.DrawerActivity;
import com.xufeifan.application.guide.GuideActivity;
import com.xufeifan.application.guide.GuideActivity2;
import com.xufeifan.application.picture.BigImgActivity;
import com.xufeifan.application.picture.DragActivity;
import com.xufeifan.application.picture.PictureActivity;
import com.xufeifan.application.process.RemoteActivity;
import com.xufeifan.application.ram.RamActivity;
import com.xufeifan.application.rx.RxjavaActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvDataBinding;
    private TextView tvRx, tvRam, tvDrawer, tvPicture, tvBig, tvDrag, tvAidl, tvRcvDrag, tvDispatch, tvGuide;
    private List<Channel> mSelectData = new ArrayList<>();
    private List<Channel> mUnSelectData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initListener();
    }

    private void initView() {
        tvDataBinding = findViewById(R.id.tvDataBinding);
        tvRx = findViewById(R.id.tvRx);
        tvRam = findViewById(R.id.tvRam);
        tvDrawer = findViewById(R.id.tvDrawer);
        tvPicture = findViewById(R.id.tvPicture);
        tvBig = findViewById(R.id.tvBig);
        tvDrag = findViewById(R.id.tvDrag);
        tvAidl = findViewById(R.id.tvAidl);
        tvRcvDrag = findViewById(R.id.tvRcvDrag);
        tvDispatch = findViewById(R.id.tvDispatch);
        tvGuide = findViewById(R.id.tvGuide);

        mSelectData.clear();
        mUnSelectData.clear();
        for (int i = 0; i < 15; i++) {
            Channel channel;
            if (i < 3) {
                channel = new Channel("0", "$-" + i, 1, false);
            } else {
                channel = new Channel("0", "$-" + i, 0, false);
            }

            mSelectData.add(channel);
        }

        for (int i = 0; i < 20; i++) {
            Channel channel = new Channel("0", "@-" + i, 0, false);
            mUnSelectData.add(channel);
        }
    }

    private void initListener() {
        tvDataBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DataBindingActivity.class));
            }
        });

        tvRx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RxjavaActivity.class));
            }
        });

        tvRam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RamActivity.class));
            }
        });

        tvDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DrawerActivity.class));
            }
        });

        tvPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PictureActivity.class));
            }
        });

        tvBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BigImgActivity.class));
            }
        });

        tvDrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DragActivity.class));
            }
        });

        tvAidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RemoteActivity.class));
            }
        });

        tvRcvDrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, com.xufeifan.application.drag.DragActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("selected", (Serializable) mSelectData);

                intent.putExtra("selected", (Serializable) mSelectData);
                intent.putExtra("unSelected", (Serializable) mUnSelectData);
                startActivity(intent);


            }
        });

        tvDispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DispatchActivity.class);
                startActivity(intent);
            }
        });

        tvGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuideActivity.class);
                startActivity(intent);
            }
        });
    }
}
