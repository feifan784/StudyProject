// IConnectService.aidl
package com.xufeifan.application;

//连接服务
interface IConnectService {

    oneway void connect();

    void disConnect();

    boolean isConnected();

}
