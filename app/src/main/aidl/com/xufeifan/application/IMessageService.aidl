package com.xufeifan.application;

import com.xufeifan.application.process.entity.Message;
import com.xufeifan.application.MessageReceiveListener;
interface IMessageService {

    void sendMessage(inout Message message);

    void registerMessageReceiveListener(MessageReceiveListener messageReceiveListener);

    void unRegisterMessageReceiveListener(MessageReceiveListener messageReceiveListener);

}
