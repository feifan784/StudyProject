package com.xufeifan.application.picture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.xufeifan.application.R;

import java.io.File;

/**
 * 图片存储优化
 * -尺寸压缩
 * -质量压缩
 * -内存复用
 * <p>
 * <p>
 * Bitmap3.0之前跟之后对比：
 * 3.0之前，像素存储于内存中，用完需要手动recycle
 * 3.0之后，bitmap对象和像素都存储于Dalvik中，垃圾回收机制回收
 */
public class PictureActivity extends AppCompatActivity {

    private ImageView ivFirst, ivSecond;
    private Bitmap mCurrentBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        initView();

        requestPermission();

    }

    private void initView() {
        ivFirst = findViewById(R.id.ivFirst);
        ivSecond = findViewById(R.id.ivSecond);
    }

    /**
     * 普通加载
     */
    private void loadFirstBitmap() {
        StringBuffer sdCard = new StringBuffer();
        sdCard.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sdCard.append("/Huawei/MagazineUnlock/11.jpg");

        mCurrentBitmap = BitmapFactory.decodeFile(sdCard.toString());
        ivFirst.setImageBitmap(mCurrentBitmap);

    }

    /**
     * 尺寸压缩+质量压缩
     */
    private void loadSecondBitmap() {
        StringBuffer sdCard = new StringBuffer();
        sdCard.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sdCard.append("/Huawei/MagazineUnlock/11.jpg");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(sdCard.toString(), options);

        int width = options.outWidth;
        options.inSampleSize = width / 200;//尺寸压缩  size>=2即可
        options.inPreferredConfig = Bitmap.Config.RGB_565;//质量压缩  一像素占两个字节
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(sdCard.toString(), options);
        ivSecond.setImageBitmap(bitmap);

    }

    /**
     * 内存复用（复用的内存必须小于等于被复用的内存空间）
     */
    private void loadThirdBitmap() {
        StringBuffer sdCard = new StringBuffer();
        sdCard.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sdCard.append("/Huawei/MagazineUnlock/11.jpg");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inBitmap = mCurrentBitmap;
        Bitmap bitmap = BitmapFactory.decodeFile(sdCard.toString());
        ivSecond.setImageBitmap(bitmap);
    }

    private void requestPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1111);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        2222);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 2222: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadFirstBitmap();
//                    loadSecondBitmap();
                    loadThirdBitmap();

                }
                return;
            }
            case 1111: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadFirstBitmap();
//                    loadSecondBitmap();
                    loadThirdBitmap();
                }
                return;
            }

        }
    }

}
