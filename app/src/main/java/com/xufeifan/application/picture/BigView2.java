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
import android.widget.Scroller;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

/**
 * 不控制宽高，XY方向均可滑动
 */
public class BigView2 extends View implements GestureDetector.OnGestureListener, View.OnTouchListener {

    //需要显示的区域
    private Rect mRect;
    //由于需要复用，所有需要option
    private BitmapFactory.Options mOption;
    //长图需要通过手势滑动来操作
    private GestureDetector mGestureDetector;
    //滑动帮助类
    private Scroller mScroller;
    //图片的宽度
    private int mImageWidth;
    //图片的高度
    private int mImageHeight;
    //控件的宽度
    private int mViewWidth;
    //控件的高度
    private int mViewHeight;
    //区域解码器
    private BitmapRegionDecoder mDecode;
    //需要展示的图片，是被复用的
    private Bitmap mBitmap;

    public BigView2(Context context) {
        this(context, null);
    }

    public BigView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRect = new Rect();
        mOption = new BitmapFactory.Options();
        mGestureDetector = new GestureDetector(context, this);
        setOnTouchListener(this);
        mScroller = new Scroller(context);
    }

    /**
     * 由使用者输入一张图片
     *
     * @param is 图片输入流
     */
    public void setImage(InputStream is) {
        mOption.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, mOption);

        mImageWidth = mOption.outWidth;
        mImageHeight = mOption.outHeight;
        //开启复用内存
        mOption.inMutable = true;
        //设置格式，减少内存
        mOption.inPreferredConfig = Bitmap.Config.RGB_565;
        mOption.inJustDecodeBounds = false;

        //创建一个区域解码器
        try {
            mDecode = BitmapRegionDecoder.newInstance(is, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //刷新
        requestLayout();
    }

    /**
     * 在控件测量中把需要内存区域获取，保存在Rect中
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取测量的view的大小
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();

        mRect.top = 0;
        mRect.left = 0;
        mRect.right = mViewWidth;
        mRect.bottom = mViewHeight;
    }

    /**
     * 画出内容
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //如果没有解码器 说明还没有图片，不需要绘制
        if (null == mDecode) {
            return;
        }
        mOption.inBitmap = mBitmap;
        //通过解码器把图解码出来，只加载矩形区域的内容
        mBitmap = mDecode.decodeRegion(mRect, mOption);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //将onTouch事件交给手势处理
        return mGestureDetector.onTouchEvent(event);
    }

    /**
     * 手指按下的回调
     */
    @Override
    public boolean onDown(MotionEvent e) {
        //如果移动还没有停止，强制停止
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
        return true;
    }

    /**
     * @param e1        手指按下去的事件 开始的坐标
     * @param e2        当前手势事件
     * @param distanceX x方向移动的距离
     * @param distanceY y方向移动的距离
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //上下移动的时候，需要改变显示的区域 改mRect
        mRect.offset((int) distanceX, (int) distanceY);
        //处理上下边界问题
        if (mRect.left < 0) {
            mRect.left = 0;
            mRect.right = mViewWidth;
        }
        if (mRect.top < 0) {
            mRect.top = 0;
            mRect.bottom = mViewHeight;
        }
        if (mRect.right > mImageWidth) {
            mRect.left = mImageWidth - mViewWidth;
            mRect.right = mImageWidth;
        }
        if (mRect.bottom > mImageHeight) {
            mRect.top = mImageHeight - mViewHeight;
            mRect.bottom = mImageHeight;
        }
        invalidate();
        return false;
    }

    /**
     * 处理滑动的惯性的问题
     *
     * @param e1        手指按下去的事件 开始的坐标
     * @param e2        当前手势事件
     * @param velocityX 每秒移动的x像素点
     * @param velocityY 每秒移动的y像素点
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling(mRect.left, mRect.top,
                (int) -velocityX, (int) -velocityY,
                0, mImageWidth - mViewWidth,
                0, mImageHeight - mViewHeight);
        return false;
    }

    /**
     * 利用onFling的计算结果 在惯性滑动过程中重新计算滑动过程中Rect的top以及bottom的值
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.isFinished()) {
            return;
        }
        //true 当前滑动还没有结束
        if (mScroller.computeScrollOffset()) {
            mRect.left = mScroller.getCurrX();
            mRect.top = mScroller.getCurrY();
            mRect.right = mRect.left + mViewHeight;
            mRect.bottom = mRect.top + mViewHeight;
            invalidate();
        }
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }
}
