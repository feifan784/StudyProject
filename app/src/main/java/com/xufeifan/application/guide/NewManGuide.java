package com.xufeifan.application.guide;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class NewManGuide {

    public static Builder with(Activity activity) {
        return new Builder(activity);
    }

    public static void resetLabel(Context context, String label) {
        SharedPreferences sp = context.getSharedPreferences("Controller_Sp", Activity.MODE_PRIVATE);
        sp.edit().putInt(label, 0).apply();
    }

}
