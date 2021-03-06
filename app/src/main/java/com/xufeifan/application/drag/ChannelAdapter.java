package com.xufeifan.application.drag;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xufeifan.application.R;

import java.util.List;

public class ChannelAdapter extends BaseMultiItemQuickAdapter<Channel, BaseViewHolder> {
    private ItemTouchHelper mTouchHelper;
    private boolean mIsEdit;
    private RecyclerView mRecyclerView;
    private long mStartTime;
    private static final long SPACE_TIME = 100;

    private OnChannelListener onChannelListener;

    public void setOnChannelListener(OnChannelListener onChannelListener) {
        this.onChannelListener = onChannelListener;
    }

    public ChannelAdapter(List<Channel> data, ItemTouchHelper helper) {
        super(data);
        this.mTouchHelper = helper;
        mIsEdit = false;

        addItemType(Channel.TYPE_ONE, R.layout.item_channel_title);
        addItemType(Channel.TYPE_THREE, R.layout.channel_rv_item);
        addItemType(Channel.TYPE_TWO, R.layout.item_channel_title);
        addItemType(Channel.TYPE_FOUR, R.layout.channel_rv_item);
        addItemType(Channel.TYPE_FIVE, R.layout.channel_five);
        addItemType(Channel.TYPE_SIX, R.layout.channel_rv_item);

    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mRecyclerView = (RecyclerView) parent;
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void convert(@NonNull final BaseViewHolder holder, final Channel item) {

        switch (holder.getItemViewType()) {
            case Channel.TYPE_ONE:
                holder.setText(R.id.tvTitle, item.getChannelName());
                holder.getView(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mIsEdit) {
                            startEditMode(true);

                        } else {
                            startEditMode(false);

                        }
                    }
                });


                break;
            case Channel.TYPE_THREE:

                holder.setText(R.id.tv_channelname, item.getChannelName())
                        .setVisible(R.id.img_edit, mIsEdit && item.getChannelType() == 0);


                holder.getView(R.id.rl_channel).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (!mIsEdit) {
                            return false;
                        }
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                mStartTime = System.currentTimeMillis();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                if (System.currentTimeMillis() - mStartTime > SPACE_TIME) {
                                    //???MOVE?????????DOWN????????????????????????????????????100ms????????????????????????starDrag
                                    mTouchHelper.startDrag(holder);
                                }
                                break;
                            case MotionEvent.ACTION_CANCEL:
                            case MotionEvent.ACTION_UP:
                                mStartTime = 0;
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });

                holder.getView(R.id.rl_channel).setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (!mIsEdit) {
                            startEditMode(true);
                        }
                        mTouchHelper.startDrag(holder);
                        return false;
                    }
                });

                holder.getView(R.id.img_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mIsEdit) {
                            if (item.getChannelType() == 1) {
                                return;
                            }

                            int otherFirstPos = getOtherFirstPos();
                            int currentPos = holder.getAdapterPosition();

                            View targetView = mRecyclerView.getLayoutManager().findViewByPosition(otherFirstPos);
                            View currentView = mRecyclerView.getLayoutManager().findViewByPosition(currentPos);

                            // ??????targetView???????????????,???indexOfChild???-1  ???????????????????????????,????????????notifyItemMoved????????????????????????????????????
                            // ??????????????????,???????????????????????????
                            if (mRecyclerView.indexOfChild(targetView) >= 0 && otherFirstPos != -1) {
                                RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
                                int spanCount = ((GridLayoutManager) manager).getSpanCount();
                                int targetX = targetView.getLeft();
                                int targetY = targetView.getTop();
                                int myChannelSize = getMyChannelSize();//???????????????????????? ?????????????????????????????????
                                if (myChannelSize % spanCount == 1) {
                                    //???????????????????????? ????????????????????????
                                    targetY -= targetView.getHeight();
                                }
                                //???????????? ????????? ????????????????????????
                                item.setItemType(Channel.TYPE_FOUR);//????????????????????????
                                item.setSelect(false);

                                if (onChannelListener != null) {
                                    onChannelListener.onMoveToOtherChannel(currentPos, otherFirstPos - 1);
                                }
                                startAnimation(currentView, targetX, targetY);
                            } else {
                                item.setItemType(Channel.TYPE_FOUR);//????????????????????????
                                item.setSelect(false);
                                if (otherFirstPos == -1) {
                                    otherFirstPos = mData.size();
                                }
                                if (onChannelListener != null) {
                                    onChannelListener.onMoveToOtherChannel(currentPos, otherFirstPos - 1);
                                }
                            }
                        }

                    }
                });

                break;
            case Channel.TYPE_TWO:
                holder.setText(R.id.tvTitle, item.getChannelName());

                break;
            case Channel.TYPE_FOUR:

                holder.setText(R.id.tv_channelname, item.getChannelName())
                        .setVisible(R.id.img_edit, mIsEdit && item.getChannelType() == 0);

                holder.getView(R.id.img_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mIsEdit) {
                            int myLastPosition = getMyLastPosition();
                            int currentPosition = holder.getAdapterPosition();
                            //???????????????View
                            View targetView = mRecyclerView.getLayoutManager().findViewByPosition(myLastPosition);
                            //???????????????????????????View
                            View currentView = mRecyclerView.getLayoutManager().findViewByPosition(currentPosition);

                            // ??????targetView???????????????,???indexOfChild???-1  ???????????????????????????,????????????notifyItemMoved????????????????????????????????????
                            // ??????????????????,???????????????????????????
                            if (mRecyclerView.indexOfChild(targetView) >= 0 && myLastPosition != -1) {
                                RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
                                int spanCount = ((GridLayoutManager) manager).getSpanCount();
                                int targetX = targetView.getLeft() + targetView.getWidth();
                                int targetY = targetView.getTop();

                                int myChannelSize = getMyChannelSize();//???????????????????????? ?????????????????????????????????
                                if (myChannelSize % spanCount == 0) {
                                    //?????????????????????????????????????????????????????????4????????????
                                    View lastFourthView = mRecyclerView.getLayoutManager().findViewByPosition(getMyLastPosition() - 3);
//                                        View lastFourthView = mRecyclerView.getChildAt(getMyLastPosition() - 3);
                                    targetX = lastFourthView.getLeft();
                                    targetY = lastFourthView.getTop() + lastFourthView.getHeight();
                                }

                                // ???????????? ????????? ???????????????????????????
                                item.setItemType(Channel.TYPE_THREE);//????????????????????????
                                item.setSelect(true);

                                if (onChannelListener != null) {
                                    onChannelListener.onMoveToMyChannel(currentPosition, myLastPosition + 1);
                                }
                                startAnimation(currentView, targetX, targetY);
                            } else {
                                item.setItemType(Channel.TYPE_THREE);//??????wde????????????
                                item.setSelect(true);

                                if (myLastPosition == -1) {
                                    myLastPosition = 0;//??????????????????????????????0
                                }
                                if (onChannelListener != null) {
                                    onChannelListener.onMoveToMyChannel(currentPosition, myLastPosition + 1);
                                }
                            }
                        }

                    }
                });

                break;


        }
    }

    private void startEditMode(boolean isEdit) {
        mIsEdit = isEdit;
        int visibleCount = mRecyclerView.getChildCount();
        Channel channel;
        for (int i = 0; i < visibleCount; i++) {
            View childAtView = mRecyclerView.getChildAt(i);
            channel = mData.get(i);
            ImageView ivEdit = (ImageView) childAtView.findViewById(R.id.img_edit);
            TextView tvName = (TextView) childAtView.findViewById(R.id.tv_channelname);
            TextView tvEdit = (TextView) childAtView.findViewById(R.id.tv_edit);
            TextView tvSort = (TextView) childAtView.findViewById(R.id.tv_sort);

            if (ivEdit != null) {
                ivEdit.setVisibility(isEdit && channel.getChannelType() == 0 ? View.VISIBLE : View.GONE);
            }

            if (tvEdit != null) {
                if (isEdit) {
                    tvEdit.setText("??????");
                } else {
                    tvEdit.setText("??????");
                }
            }

            if (tvSort != null) {
                if (mIsEdit) {
                    tvSort.setText("??????????????????");
                } else {
                    tvSort.setText("??????????????????");
                }
            }

        }
    }

    /**
     * ??????????????????????????????
     */
    private int getOtherFirstPos() {
        Channel channel;
        for (int i = 0; i < mData.size(); i++) {
            channel = mData.get(i);
            if (channel.getItemType() == Channel.TYPE_FOUR) {
                return i;
            }
        }
        return -1;
    }

    public int getMyChannelSize() {
        int size = 0;
        for (int i = 0; i < mData.size(); i++) {
            Channel channel = (Channel) mData.get(i);
            if (channel.getItemType() == Channel.TYPE_THREE) {
                size++;
            }
        }
        return size;
    }

    private void startAnimation(final View currentView, int targetX, int targetY) {
        final ViewGroup parent = (ViewGroup) mRecyclerView.getParent();
        final ImageView mirrorView = addMirrorView(parent, currentView);
        TranslateAnimation animator = getTranslateAnimator(currentView, targetX, targetY);
        currentView.setVisibility(View.INVISIBLE);//????????????
        mirrorView.startAnimation(animator);
        animator.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                parent.removeView(mirrorView);//?????????????????????View
                if (currentView.getVisibility() == View.INVISIBLE) {
                    currentView.setVisibility(View.VISIBLE);//???????????????View
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * ????????????????????? ??????View
     */
    private ImageView addMirrorView(ViewGroup parent, View view) {
        view.destroyDrawingCache();
        //????????????Cache?????? ???????????????view.getDrawingCache()???????????????Cache??????
        view.setDrawingCacheEnabled(true);
        ImageView mirrorView = new ImageView(view.getContext());
        //?????????view???Cache??????
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        mirrorView.setImageBitmap(bitmap);
        //?????????cache??????
        view.setDrawingCacheEnabled(false);
        int[] locations = new int[2];
        view.getLocationOnScreen(locations);//????????????View?????????
        int[] parenLocations = new int[2];
        mRecyclerView.getLocationOnScreen(parenLocations);//??????RecyclerView????????????
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        params.setMargins(locations[0], locations[1] - parenLocations[1], 0, 0);
        parent.addView(mirrorView, params);//???RecyclerView???Parent?????????????????????View???parent??????FrameLayout????????????????????????????????????
        return mirrorView;
    }

    /**
     * ??????????????????
     */
    private TranslateAnimation getTranslateAnimator(final View currentView, float targetX, float targetY) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.ABSOLUTE, currentView.getLeft(),
                Animation.ABSOLUTE, targetX,
                Animation.ABSOLUTE, currentView.getTop(),
                Animation.ABSOLUTE, targetY);
        // RecyclerView??????????????????250ms ????????????360ms ??????????????????????????????????????? remove(view)?????? ????????????
        translateAnimation.setDuration(360);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    public int getMyLastPosition() {
        for (int i = mData.size() - 1; i > -1; i--) {
            Channel channel = (Channel) mData.get(i);
            if (Channel.TYPE_THREE == channel.getItemType()) {
                //???????????????????????????
                return i;
            }
        }
        return -1;
    }
}
