package com.xufeifan.application.guide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.model.HighLight;
import com.xufeifan.application.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private TextView tvTarget1, tvTarget2, tvTarget3;
    private int step = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        EventBus.getDefault().register(this);

        tvTarget1 = findViewById(R.id.tvTarget1);
        tvTarget2 = findViewById(R.id.tvTarget2);
        tvTarget3 = findViewById(R.id.tvTarget3);

        NewManGuide.with(this)
                .setLabel("guide1")
                .setAlwaysShow(true)
                .addGuidePage(new GuidePage()
                        .addHighLight(tvTarget1)
                        .setLayoutResId(R.layout.view_guide_simple, Gravity.START))
                .show();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(GuideEvent event) {
        step++;
        if (step == 2) {
            NewManGuide.with(this)
                    .setLabel("guide2")
                    .setAlwaysShow(true)
                    .addGuidePage(new GuidePage()
                            .addHighLight(tvTarget2)
                            .setLayoutResId(R.layout.view_guide_simple2, Gravity.TOP))
                    .show();
        } else if (step == 3) {
            NewManGuide.with(this)
                    .setLabel("guide3")
                    .setAlwaysShow(true)
                    .addGuidePage(new GuidePage()
                            .addHighLight(tvTarget3)
                            .setLayoutResId(R.layout.view_guide_simple3, Gravity.BOTTOM))
                    .show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
