package com.xufeifan.application.dispatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.xufeifan.application.R;

public class DispatchActivity extends AppCompatActivity {

    private SpecialConstrantLayout consDispatch;
    private TextView tvDispatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);

        initView();

        initListener();
    }

    private void initView() {
        consDispatch = findViewById(R.id.consDispatch);
        tvDispatch = findViewById(R.id.tvDisPatch);
    }

    private void initListener() {

        consDispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("xfftll", "consDispatch:    click");
            }
        });

        tvDispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("xfftll", "tvDispatch:    click");
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("xfftll", "activity: --dispatchTouchEvent" + ev.getAction());
        return super.dispatchTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("xfftll", "activity: --onTouchEvent" + event.getAction());
        return true;
    }
}
