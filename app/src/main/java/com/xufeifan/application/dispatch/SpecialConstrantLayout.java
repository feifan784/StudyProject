package com.xufeifan.application.dispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.constraintlayout.widget.ConstraintLayout;

public class SpecialConstrantLayout extends ConstraintLayout {


    public SpecialConstrantLayout(Context context) {
        super(context);
    }

    public SpecialConstrantLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpecialConstrantLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("xfftll", "ViewGroup: --dispatchTouchEvent" + ev.getAction());
//        return super.dispatchTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("xfftll", "ViewGroup: --onInterceptTouchEvent" + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("xfftll", "ViewGroup: --onTouchEvent" + event.getAction());
        return super.onTouchEvent(event);
    }
}
