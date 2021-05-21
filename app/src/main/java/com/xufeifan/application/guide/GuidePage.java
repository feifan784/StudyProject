package com.xufeifan.application.guide;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GuidePage {
    private List<HightLightInterface> highLightViewList = new ArrayList<>();
    private boolean everywhereCancelable = true;
    private int backgroundColor = 0;
    private int layoutResId;
    private int[] clickToDismissIds;
    private int gravity;

    public GuidePage() {
    }

    public GuidePage addHighLight(View view) {
        return addHighLight(view, HightLightInterface.Shape.RECTANGLE, 0, 1);
    }

    public GuidePage addHighLight(View view, HightLightInterface.Shape shape, int round, int padding) {
        HighLightView highlight = new HighLightView(view, shape, round, padding);

        highLightViewList.add(highlight);
        return this;
    }

    public boolean isEverywhereCancelable() {
        return everywhereCancelable;
    }

    public List<HightLightInterface> getHighLights() {
        return highLightViewList;
    }


    public GuidePage setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getLayoutResId() {
        return layoutResId;
    }

    public GuidePage setLayoutResId(int layoutResId, int gravity, int... id) {
        this.layoutResId = layoutResId;
        this.gravity = gravity;
        clickToDismissIds = id;
        return this;
    }

    public int[] getClickToDismissIds() {
        return clickToDismissIds;
    }

    public RelativeGuide getRelativeGuide() {
        return new RelativeGuide(highLightViewList.get(0), layoutResId, gravity, 0);
    }
}
