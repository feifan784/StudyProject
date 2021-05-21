package com.xufeifan.application.guide;

import android.graphics.RectF;
import android.view.View;

public interface HightLightInterface {

    Shape getShape();

    RectF getRectF(View view);

    /**
     * 当shape为CIRCLE时调用此方法获取半径
     */
    float getRadius();

    /**
     * 获取圆角，仅当shape = Shape.ROUND_RECTANGLE才调用次方法
     */
    int getRound();

    public enum Shape {
        CIRCLE,//圆形
        RECTANGLE, //矩形
        OVAL,//椭圆
        ROUND_RECTANGLE;//圆角矩形
    }
}
