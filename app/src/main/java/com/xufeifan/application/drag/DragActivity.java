package com.xufeifan.application.drag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.xufeifan.application.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿支付宝子应用拖拽调整
 */
public class DragActivity extends AppCompatActivity implements OnChannelListener {

    private RecyclerView rcvDrag;
    private List<Channel> mSelectData;
    private List<Channel> mUnSelectedData;
    private List<Channel> mDatas = new ArrayList<>();
    private ChannelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag2);
        mDatas.clear();

        //我的频道标题
        Channel channel = new Channel();
        channel.setItemType(Channel.TYPE_ONE);
        channel.setChannelName("我的频道");
        mDatas.add(channel);
        //我的频道item
        mSelectData = (List<Channel>) getIntent().getSerializableExtra("selected");
        mUnSelectedData = (List<Channel>) getIntent().getSerializableExtra("unSelected");
        setDataType(mSelectData, Channel.TYPE_THREE);
        setDataType(mUnSelectedData, Channel.TYPE_FOUR);
        mDatas.addAll(mSelectData);
        //其他频道标题
        Channel moreChannel = new Channel();
        moreChannel.setItemType(Channel.TYPE_TWO);
        moreChannel.setChannelName("频道推荐");
        mDatas.add(moreChannel);
        //其他频道item
        mDatas.addAll(mUnSelectedData);

        rcvDrag = findViewById(R.id.rcvDrag);


        ItemDragHelperCallBack callBack = new ItemDragHelperCallBack(this);
        ItemTouchHelper helper = new ItemTouchHelper(callBack);
        helper.attachToRecyclerView(rcvDrag);

        adapter = new ChannelAdapter(mDatas, helper);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        rcvDrag.setLayoutManager(gridLayoutManager);
        rcvDrag.setAdapter(adapter);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = adapter.getItemViewType(position);
                return itemViewType == Channel.TYPE_ONE || itemViewType == Channel.TYPE_TWO ? 4 : 1;
            }
        });
        adapter.setOnChannelListener(this);

    }

    private void setDataType(List<Channel> datas, int typeOtherChannel) {
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setItemType(typeOtherChannel);
        }
    }

    @Override
    public void onItemMove(int starPos, int endPos) {

        if (starPos < 0 || endPos < 0) {
            return;
        }

        move(starPos, endPos, false);


    }

    @Override
    public void onMoveToMyChannel(int startPos, int endPos) {
        move(startPos, endPos, true);
    }

    @Override
    public void onMoveToOtherChannel(int startPos, int endPos) {

        move(startPos, endPos, false);
    }

    @Override
    public void onFinish(String channelName) {

    }

    private void move(int startPos, int endPos, boolean isAdd) {

        Channel startChannel = mDatas.get(startPos);
        mDatas.remove(startChannel);
        mDatas.add(endPos, startChannel);

        adapter.notifyItemMoved(startPos, endPos);


    }
}
