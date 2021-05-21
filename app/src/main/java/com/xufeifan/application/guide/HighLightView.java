package com.xufeifan.application.guide;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * 高光View
 */
public class HighLightView implements HightLightInterface {

    private View view;
    private HightLightInterface.Shape shape;
    private int round;
    private int padding;
    private RectF rectF;

    public HighLightView(View view, HightLightInterface.Shape shape, int round, int padding) {
        this.view = view;
        this.shape = shape;
        this.round = round;
        this.padding = padding;
    }

    @Override
    public RectF getRectF(View parent) {
        if (view == null) {
            throw new IllegalArgumentException("the highlight view is null!");
        }

        if (rectF == null) {
            rectF = new RectF();
            Rect locationInView = ViewUtils.getLocationInView(parent, view);
            rectF.left = locationInView.left - padding;
            rectF.top = locationInView.top - padding;
            rectF.right = locationInView.right + padding;
            rectF.bottom = locationInView.bottom + padding;
        }
        return rectF;
    }

    @Override
    public float getRadius() {
        if (view == null) {
            throw new IllegalArgumentException("the highLight view is null!");
        }
        return Math.max(view.getMeasuredHeight(), view.getMeasuredWidth()) + padding;
    }

    @Override
    public int getRound() {
        return round;
    }

    @Override
    public HightLightInterface.Shape getShape() {
        return shape;
    }

    public int getPadding() {
        return padding;
    }
}
