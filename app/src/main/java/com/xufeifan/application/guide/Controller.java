package com.xufeifan.application.guide;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;


public class Controller {
    private Activity activity;
    private boolean alwaysShow;
    private int showCounts;
    private String label;
    private View anchor;
    private FrameLayout mParentView;
    private int indexOfChild = -1;//使用anchor时记录的在父布局的位置
    private SharedPreferences sp;
    private List<GuidePage> guidePageList;
    private int current;//当前页

    public Controller(Builder builder) {
        activity = builder.activity;
        alwaysShow = builder.alwaysShow;
        showCounts = builder.showCounts;
        label = builder.label;
        guidePageList = builder.guidePageList;
        anchor = builder.anchor;
        if (anchor == null) {
            anchor = activity.findViewById(android.R.id.content);
        }
        if (anchor instanceof FrameLayout) {
            mParentView = (FrameLayout) anchor;
        } else {
            FrameLayout frameLayout = new FrameLayout(activity);
            ViewGroup parent = (ViewGroup) anchor.getParent();
            indexOfChild = parent.indexOfChild(anchor);
            parent.removeView(anchor);
            if (indexOfChild >= 0) {
                parent.addView(frameLayout, indexOfChild, anchor.getLayoutParams());
            } else {
                parent.addView(frameLayout, anchor.getLayoutParams());
            }
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            frameLayout.addView(anchor, lp);
            mParentView = frameLayout;
        }
        sp = activity.getSharedPreferences("Controller_Sp", Activity.MODE_PRIVATE);
    }

    public void show() {
        final int showed = sp.getInt(label, 0);
        if (!alwaysShow) {
            if (showed >= showCounts) {
                return;
            }
        }

        mParentView.post(new Runnable() {
            @Override
            public void run() {
                if (guidePageList == null || guidePageList.size() == 0) {
                    throw new IllegalStateException("there is no guide to show!! Please add at least one Page.");
                }
                current = 0;
                showGuidePage();
                sp.edit().putInt(label, showed + 1).apply();
            }
        });
    }

    private void showGuidePage() {
        final GuidePage page = guidePageList.get(current);
        GuideLayout guideLayout = new GuideLayout(activity, page, this);
        guideLayout.setOnGuideLayoutDismissListener(new GuideLayout.OnGuideLayoutDismissListener() {
            @Override
            public void onGuideLayoutDismiss(GuideLayout guideLayout) {

            }
        });
        mParentView.addView(guideLayout, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


}
