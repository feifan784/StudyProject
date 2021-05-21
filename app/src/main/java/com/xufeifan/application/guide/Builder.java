package com.xufeifan.application.guide;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Builder {
    Activity activity;
    boolean alwaysShow;
    int showCounts = 1;
    String label;
    View anchor;
    List<GuidePage> guidePageList = new ArrayList<>();


    public Builder(Activity activity) {
        this.activity = activity;
    }

    public boolean isAlwaysShow() {
        return alwaysShow;
    }

    /**
     * 常驻，debug模式可用
     */
    public Builder setAlwaysShow(boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
        return this;
    }

    public int getShowCounts() {
        return showCounts;
    }

    /**
     * 设置展示次数
     */
    public Builder setShowCounts(int showCounts) {
        this.showCounts = showCounts;
        return this;
    }

    public Builder setLabel(String label) {
        this.label = label;
        return this;
    }

    public Builder anchor(View anchor) {
        this.anchor = anchor;
        return this;
    }

    public Builder addGuidePage(GuidePage page) {
        guidePageList.add(page);
        return this;
    }

    public Controller show() {
        check();
        Controller controller = new Controller(this);
        controller.show();
        return controller;
    }

    private void check() {
        if (TextUtils.isEmpty(label)) {
            throw new IllegalArgumentException("the param 'label' is missing, please call setLabel()");
        }
        if (activity == null) {
            throw new IllegalStateException("activity is null, please make sure that fragment is showing when call NewbieGuide");
        }
    }
}
