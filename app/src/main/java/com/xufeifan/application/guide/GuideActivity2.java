package com.xufeifan.application.guide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.app.hubert.guide.NewbieGuide;
import com.xufeifan.application.R;

public class GuideActivity2 extends AppCompatActivity {
    private TextView tvTarget1, tvTarget2, tvTarget3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        tvTarget1 = findViewById(R.id.tvTarget1);
        tvTarget2 = findViewById(R.id.tvTarget2);
        tvTarget3 = findViewById(R.id.tvTarget3);

        NewbieGuide.with(this)
                .setLabel("guide1")
                .setShowCounts(3)//控制次数
                .alwaysShow(true)//总是显示，调试时可以打开
                .addGuidePage(com.app.hubert.guide.model.GuidePage.newInstance()
                        .addHighLight(tvTarget1)
                        .setLayoutRes(R.layout.view_guide_simple))
                .addGuidePage(com.app.hubert.guide.model.GuidePage.newInstance()
                        .addHighLight(tvTarget2)
                        .setLayoutRes(R.layout.view_guide_simple2))
                .addGuidePage(com.app.hubert.guide.model.GuidePage.newInstance()
                        .addHighLight(tvTarget3)
                        .setLayoutRes(R.layout.view_guide_simple3))
                .show();
    }
}
