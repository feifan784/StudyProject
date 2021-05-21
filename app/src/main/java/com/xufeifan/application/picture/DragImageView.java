package com.xufeifan.application.picture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class DragImageView extends ImageView {
    private Drawable mDrawable;
    private Rect mDrawableRect;
    private Context mContext;
    private float mRation_WH = 0;
    private float mOldX = 0;
    private float mOldY = 0;
    private double mD1;
    private boolean isFirst = true;

    private int NONE = 0;// 无操作
    private int SINGAL_MOVE = 1;// 单点移动
    private int MUTIL_MOVE = 2;// 双点拖拽
    private int mStatus = 0;

    public DragImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mDrawableRect = new Rect();
    }

    public DragImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mDrawableRect = new Rect();
    }

    public DragImageView(Context context) {
        super(context);
        this.mContext = context;
        mDrawableRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // getIntrinsicHeight为返回对象的实际高度
        if (mDrawable == null || mDrawable.getIntrinsicHeight() == 0
                || mDrawable.getIntrinsicWidth() == 0) {
            return;
        }
        setBounds(); // 设置图片
        mDrawable.draw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mOldX = event.getX();
                mOldY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                mStatus = NONE;
                checkBounds();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 1)
                    mStatus = SINGAL_MOVE;
                else
                    mStatus = MUTIL_MOVE;
                onTouchMove(event);
                break;
            default:
                break;
        }
        return true;
    }

    private void onTouchMove(MotionEvent event) {
        // 判断有几个触摸点
        if (mStatus == SINGAL_MOVE) {
            int offsetWidth = (int) (event.getX() - mOldX);
            int offsetHeight = (int) (event.getY() - mOldY);
            mOldX = event.getX();
            mOldY = event.getY();
            mDrawableRect.offset(offsetWidth, offsetHeight);
            invalidate();
        } else if (mStatus == MUTIL_MOVE) {
            float X0 = event.getX(0);
            float Y0 = event.getY(0);
            float X1 = event.getX(1);
            float Y1 = event.getY(1);
            double mD2 = Math.sqrt(Math.pow(X0 - X1, 2) + Math.pow(Y0 - Y1, 2));

            if (mD1 < mD2) {
                // 放大操作
                if (mDrawableRect.width() < mContext.getResources()
                        .getDisplayMetrics().widthPixels * 2) {
                    int offsetwidth = 10;
                    int offsettop = (int) (offsetwidth / mRation_WH);
                    mDrawableRect.set(mDrawableRect.left - offsetwidth,
                            mDrawableRect.top - offsettop, mDrawableRect.right
                                    + offsetwidth, mDrawableRect.bottom
                                    + offsettop);
                    invalidate();
                }

            } else {
                // 设置为只能缩小为屏幕的1/3,可以根据需要自己调整
                if (mDrawableRect.width() > mContext.getResources()
                        .getDisplayMetrics().widthPixels / 3) {
                    int offsetwidth = 10;
                    int offsettop = (int) (offsetwidth / mRation_WH);
                    mDrawableRect.set(mDrawableRect.left + offsetwidth,
                            mDrawableRect.top + offsettop, mDrawableRect.right
                                    - offsetwidth, mDrawableRect.bottom
                                    - offsettop);
                    invalidate();
                }
            }
            mD1 = mD2;
        }

    }

    /**
     * 设置mDrawable
     */
    public void setBounds() {
        if (isFirst) {
            // 图片宽高比
            mRation_WH = (float) mDrawable.getIntrinsicWidth()
                    / (float) mDrawable.getIntrinsicHeight();
            // 取屏幕宽和图片宽较小的一个
            int px_w = Math.min(getWidth(),
                    dp2px(mContext, mDrawable.getIntrinsicWidth()));
            int px_h = (int) (px_w / mRation_WH);// 获得图片高
            int left = (getWidth() - px_w) / 2;
            int top = (getHeight() - px_h) / 2;
            int right = px_w + left;
            int bottom = px_h + top;
            mDrawableRect.set(left, top, right, bottom);
            isFirst = false;
        }
        mDrawable.setBounds(mDrawableRect);

    }

    /**
     * 检测边框范围
     */
    public void checkBounds() {
        int newLeft = mDrawableRect.left;
        int newTop = mDrawableRect.top;
        boolean isChange = false;
        // 向左移动范围<=屏幕宽度
        if (newLeft < -mDrawableRect.width()) {
            newLeft = -mDrawableRect.width();
            isChange = true;
        }
        // 向上移动范围<=屏幕高度
        if (newTop < -mDrawableRect.height()) {
            newTop = -mDrawableRect.height();
            isChange = true;
        }
        // 向右移动范围<=屏幕宽度
        if (newLeft > getWidth()) {
            newLeft = getWidth();
            isChange = true;
        }
        // 向下移动范围<=屏幕高度
        if (newTop > getHeight()) {
            newTop = getHeight();
            isChange = true;
        }
        if (isChange) {
            mDrawableRect.offsetTo(newLeft, newTop);
            invalidate();
        }
    }

    public Drawable getmDrawable() {
        return mDrawable;
    }

    public void setmDrawable(Drawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    /**
     * 将dp单位换算成px
     * @param context
     * @param value
     * @return
     */
    public int dp2px(Context context, int value) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }
}
