package com.xufeifan.application.drag;

public interface OnChannelListener {

    void onItemMove(int starPos, int endPos);

    void onMoveToMyChannel(int startPos, int endPos);

    void onMoveToOtherChannel(int startPos, int endPos);

    void onFinish(String channelName);

}
