package com.xufeifan.application;

import com.xufeifan.application.process.entity.Message;

interface MessageReceiveListener {

    void receiveMessage(in Message message);

}
