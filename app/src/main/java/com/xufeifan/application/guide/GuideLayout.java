package com.xufeifan.application.guide;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.xufeifan.application.guide.HightLightInterface.Shape.CIRCLE;
import static com.xufeifan.application.guide.HightLightInterface.Shape.OVAL;
import static com.xufeifan.application.guide.HightLightInterface.Shape.RECTANGLE;
import static com.xufeifan.application.guide.HightLightInterface.Shape.ROUND_RECTANGLE;

public class GuideLayout extends FrameLayout {
    public static final int DEFAULT_BACKGROUND_COLOR = 0xb2000000;
    private Controller controller;
    private Paint mPaint;
    private GuidePage guidePage;
    private int touchSlop;
    private float downX, downY;


    public GuideLayout(Context context, GuidePage guidePage, Controller controller) {
        super(context);
        init();
        setGuidePage(guidePage);
        this.controller = controller;
    }

    public GuideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GuideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        mPaint.setXfermode(xfermode);

        //设置画笔遮罩滤镜,可以传入BlurMaskFilter或EmbossMaskFilter，前者为模糊遮罩滤镜而后者为浮雕遮罩滤镜
        //这个方法已经被标注为过时的方法了，如果你的应用启用了硬件加速，你是看不到任何阴影效果的
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER));
        //关闭当前view的硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        //ViewGroup默认设定为true，会使onDraw方法不执行，如果复写了onDraw(Canvas)方法，需要清除此标记
        setWillNotDraw(false);

        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    }

    private void setGuidePage(final GuidePage guidePage) {
        this.guidePage = guidePage;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guidePage.isEverywhereCancelable()) {
                    remove();
                    EventBus.getDefault().post(new GuideEvent());
                }
            }
        });
    }

    public void remove() {
        dismiss();
    }

    private void dismiss() {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
            if (listener != null) {
                listener.onGuideLayoutDismiss(this);
            }

        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float upX = event.getX();
                float upY = event.getY();
                if (Math.abs(upX - downX) < touchSlop && Math.abs(upY - downY) < touchSlop) {
                    List<HightLightInterface> highLights = guidePage.getHighLights();
                    for (HightLightInterface highLight : highLights) {
                        RectF rectF = highLight.getRectF((ViewGroup) getParent());
                        if (rectF.contains(upX, upY)) {
                            return true;
                        }
                    }
                    performClick();
                }
                break;

        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int backgroundColor = guidePage.getBackgroundColor();
        canvas.drawColor(backgroundColor == 0 ? DEFAULT_BACKGROUND_COLOR : backgroundColor);
        drawHighlights(canvas);
    }

    private void drawHighlights(Canvas canvas) {
        List<HightLightInterface> highLights = guidePage.getHighLights();
        if (highLights != null) {
            for (HightLightInterface highLight : highLights) {
                RectF rectF = highLight.getRectF((ViewGroup) getParent());
                switch (highLight.getShape()) {
                    case CIRCLE:
                        canvas.drawCircle(rectF.centerX(), rectF.centerY(), highLight.getRadius(), mPaint);
                        break;
                    case OVAL:
                        canvas.drawOval(rectF, mPaint);
                        break;
                    case ROUND_RECTANGLE:
                        canvas.drawRoundRect(rectF, highLight.getRound(), highLight.getRound(), mPaint);
                        break;
                    case RECTANGLE:
                    default:
                        canvas.drawRect(rectF, mPaint);
                        break;
                }
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addCustomToLayout(guidePage);
    }

    public void addCustomToLayout(GuidePage guidePage) {
        RelativeGuide relativeGuide = guidePage.getRelativeGuide();
        addView(relativeGuide.getGuideLayout((ViewGroup) getParent()));

    }

    private OnGuideLayoutDismissListener listener;

    public interface OnGuideLayoutDismissListener {
        void onGuideLayoutDismiss(GuideLayout guideLayout);
    }

    public void setOnGuideLayoutDismissListener(OnGuideLayoutDismissListener listener) {
        this.listener = listener;
    }
}
