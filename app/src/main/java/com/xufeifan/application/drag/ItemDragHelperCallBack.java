package com.xufeifan.application.drag;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class ItemDragHelperCallBack extends ItemTouchHelper.Callback {

    private OnChannelListener onChannelListener;

    public ItemDragHelperCallBack(OnChannelListener listener) {
        this.onChannelListener = listener;
    }

    public void setOnChannelListener(OnChannelListener listener) {
        this.onChannelListener = listener;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        int dragFlag;
        if (manager instanceof GridLayoutManager || manager instanceof StaggeredGridLayoutManager) {
            //非线性布局，有上下左右方向
            dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.UP | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN;
        } else {
            //线性布局，只有上下
            dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }

        return makeMovementFlags(dragFlag, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

        //不同类型间不能移动
        if (viewHolder.getItemViewType() != target.getItemViewType()) {

//            if ((viewHolder.getItemViewType() == 3 && target.getItemViewType() == 4) || (viewHolder.getItemViewType() == 4 && target.getItemViewType() == 3)) {
//                if (onChannelListener != null) {
//                    onChannelListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//                }
//                return true;
//
//            }

            return false;
        }
        //前三个不可移动
        if (target.getAdapterPosition() < 4 && target.getAdapterPosition() > 0) {
            return false;
        }

        if (onChannelListener != null) {
            onChannelListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }

        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
