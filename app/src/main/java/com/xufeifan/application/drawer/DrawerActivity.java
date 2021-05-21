package com.xufeifan.application.drawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.xufeifan.application.R;

import org.yang.lib.VerticalDrawerView;


public class DrawerActivity extends AppCompatActivity {

    private VerticalDrawerView drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        drawer = findViewById(R.id.drawer);

        Drawable drawable = getResources().getDrawable(R.drawable.best_oval_red_label);
        // add indicator
        drawer.setIndicator(drawable, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        drawer.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_account_red));

        // add content. using same content
//        drawer.setCollapsedView(contentView, dp2px(33));
//        drawer.setExpandedView(contentView);

        View collapseContentView = LayoutInflater.from(this).inflate(R.layout.view_top, null);
        View expandContentView = LayoutInflater.from(this).inflate(R.layout.view_bottom, null);


        // or using different content
        drawer.setCollapsedView(collapseContentView);
        drawer.setExpandedView(expandContentView);
    }
}
