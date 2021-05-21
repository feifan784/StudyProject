package com.xufeifan.application.picture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xufeifan.application.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * 超大图片加载
 * -使用图片压缩来加载图片，回看不清图片细节
 * -使用BitmapRegionDecoder来解决
 */

public class BigImgActivity extends AppCompatActivity {

    private BigView2 view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img);

        view = findViewById(R.id.liv);

        try {
            InputStream is = getAssets().open("22.png");
            view.setImage(is);
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
