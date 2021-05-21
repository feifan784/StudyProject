package com.xufeifan.application.guide;

import android.graphics.RectF;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 控制应道提示布局显示位置
 */
public class RelativeGuide {
    @IntDef({Gravity.START, Gravity.TOP, Gravity.END, Gravity.BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    @interface LimitGravity {
    }

    public int layout;
    public int gravity;
    public int padding;
    public HightLightInterface highLight;

    public RelativeGuide(HightLightInterface highLight, int layout, @LimitGravity int gravity, int padding) {
        this.highLight = highLight;
        this.layout = layout;
        this.gravity = gravity;
        this.padding = padding;
    }

    public View getGuideLayout(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        RectF rectF = highLight.getRectF(viewGroup);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        switch (gravity) {
            case Gravity.START:
                layoutParams.leftMargin = 10;
                layoutParams.topMargin = (int) (rectF.top + rectF.height() / 2);
                layoutParams.rightMargin = (int) (rectF.right + 10);
                break;
            case Gravity.TOP:
                layoutParams.topMargin = (int) (rectF.top / 2);
                break;
            case Gravity.END:
                layoutParams.leftMargin = (int) (rectF.right + 10);
                layoutParams.topMargin = (int) (rectF.top + rectF.height() / 2);
                layoutParams.rightMargin = 10;

                break;
            case Gravity.BOTTOM:
                layoutParams.topMargin = (int) (rectF.bottom + 100);
                break;

        }

        view.setLayoutParams(layoutParams);
        return view;
    }

}
