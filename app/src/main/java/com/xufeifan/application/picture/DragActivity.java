package com.xufeifan.application.picture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xufeifan.application.R;

public class DragActivity extends AppCompatActivity {

    private DragImageView div;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);

        div = findViewById(R.id.div);

        div.setmDrawable(getResources().getDrawable(R.drawable.flower));

    }
}
