package com.xufeifan.application.picture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class LargeImageView extends ImageView implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private Context mContext;
    private int mWidth;
    private int mHeight;
    private BitmapRegionDecoder mDecoder;
    private GestureDetector mGestureDetector;
    private int mLastX, mLastY;
    private Rect mRect = new Rect();

    public LargeImageView(Context context) {
        super(context);
        mContext = context;
    }

    public LargeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public LargeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setOnTouchListener(this);
    }


    public void setInputStream(InputStream inputStream) throws IOException {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        mWidth = options.outWidth;
        mHeight = options.outHeight;

        mDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
        mGestureDetector = new GestureDetector(mContext, this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //显示图片
        Bitmap bitmap = mDecoder.decodeRegion(mRect, null);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int imageWidth = mWidth;
        int imageHeight = mHeight;

        //默认显示图片的中心区域
        mRect.left = imageWidth / 2 - width / 2;
        mRect.top = imageHeight / 2 - height / 2;
        mRect.right = mRect.left + width;
        mRect.bottom = mRect.top + height;

    }

    @Override
    public boolean onDown(MotionEvent e) {
        mLastX = (int) e.getRawX();
        mLastY = (int) e.getRawY();


        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float v, float v1) {
        int x = (int) e2.getRawX();
        int y = (int) e2.getRawY();

        move(x, y);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    private void move(int x, int y) {
        int deltaX = x - mLastX;
        int deltaY = y - mLastY;
//        //X方向
        if (mWidth > getWidth()) {
            mRect.offset(-deltaX, 0);
            if (mRect.right > mWidth) {
                mRect.right = mWidth;
                mRect.left = mWidth - getWidth();
            }
            if (mRect.left < 0) {
                mRect.left = 0;
                mRect.right = getWidth();
            }
            invalidate();
        }

        //Y方向
        if (mHeight > getHeight()) {
            mRect.offset(0, -deltaY);
            //到达底部
            if (mRect.bottom > mHeight) {
                mRect.bottom = mHeight;
                mRect.top = mHeight - getHeight();
            }
            //到达顶部
            if (mRect.top < 0) {
                mRect.top = 0;
                mRect.bottom = getHeight();
            }
            invalidate();
        }
        mLastX = x;
        mLastY = y;

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mGestureDetector.onTouchEvent(motionEvent);
    }
}
