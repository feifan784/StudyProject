package com.xufeifan.application.drag;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class Channel extends DataSupport implements Serializable, MultiItemEntity {

    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    public static final int TYPE_THREE = 3;
    public static final int TYPE_FOUR = 4;
    public static final int TYPE_FIVE = 5;
    public static final int TYPE_SIX = 6;

    private int itemType;

    private String channelId;
    private String channelName;

    //0可移除 1不可移除
    private int channelType;

    private boolean isSelect;

    public Channel() {
    }

    public Channel(String channelId, String channelName, int channelType, boolean isSelect) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelType = channelType;
        this.isSelect = isSelect;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
