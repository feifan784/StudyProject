package com.xufeifan.application.process;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.xufeifan.application.IConnectService;
import com.xufeifan.application.IMessageService;
import com.xufeifan.application.IServiceManager;
import com.xufeifan.application.MessageReceiveListener;
import com.xufeifan.application.process.entity.Message;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 管理和提供Remote进程的连接和消息服务
 */
public class RemoteService extends Service {

    private boolean isConnect;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            bundle.setClassLoader(Message.class.getClassLoader());
            Message message = bundle.getParcelable("messenger");

            Toast.makeText(RemoteService.this, message.getContent(), Toast.LENGTH_SHORT).show();

            //回复
            try {
                Messenger clientMessenger = msg.replyTo;
                Message reply = new Message();
                reply.setContent("message from reply by remote");
                android.os.Message data = new android.os.Message();
                Bundle bundleReply = new Bundle();
                bundleReply.putParcelable("messenger_reply", reply);
                data.setData(bundleReply);
                clientMessenger.send(data);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    };
    private RemoteCallbackList<MessageReceiveListener> messageRemoteCallback = new RemoteCallbackList<>();

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    private ScheduledFuture future;

    private Messenger messenger = new Messenger(handler);

    @Override
    public IBinder onBind(Intent intent) {
        return manager.asBinder();
    }

    private IConnectService connectService = new IConnectService.Stub() {
        @Override
        public void connect() throws RemoteException {
            try {
                Thread.sleep(3000);
                isConnect = true;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RemoteService.this, "connect", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            future = scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {

                    int size = messageRemoteCallback.beginBroadcast();

                    for (int i = 0; i < size; i++) {
                        Message message = new Message();
                        message.setContent("this message from remote");
                        try {
                            messageRemoteCallback.getBroadcastItem(i).receiveMessage(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    messageRemoteCallback.finishBroadcast();

                }
            }, 5000, 5000, TimeUnit.MILLISECONDS);

        }

        @Override
        public void disConnect() throws RemoteException {
            isConnect = false;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RemoteService.this, "disconnect", Toast.LENGTH_SHORT).show();
                }
            });
            future.cancel(true);
        }

        @Override
        public boolean isConnected() throws RemoteException {
            return isConnect;
        }
    };

    private IMessageService iMessageService = new IMessageService.Stub() {
        @Override
        public void sendMessage(final Message message) throws RemoteException {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RemoteService.this, message.getContent(), Toast.LENGTH_SHORT).show();
                    Log.i("xfftll", message.getContent());
                }
            });

            if (isConnect) {
                message.setSendSuccess(true);
            } else {
                message.setSendSuccess(false);
            }

        }

        @Override
        public void registerMessageReceiveListener(MessageReceiveListener messageReceiveListener) throws RemoteException {
            if (messageReceiveListener != null) {
                messageRemoteCallback.register(messageReceiveListener);
            }
        }

        @Override
        public void unRegisterMessageReceiveListener(MessageReceiveListener messageReceiveListener) throws RemoteException {
            if (messageReceiveListener != null) {
                messageRemoteCallback.unregister(messageReceiveListener);
            }
        }
    };

    private IServiceManager manager = new IServiceManager.Stub() {
        @Override
        public IBinder getService(String serviceName) throws RemoteException {
            if (IConnectService.class.getSimpleName().equals(serviceName)) {
                return connectService.asBinder();
            } else if (IMessageService.class.getSimpleName().equals(serviceName)) {
                return iMessageService.asBinder();
            } else if (Messenger.class.getSimpleName().equals(serviceName)) {
                return messenger.getBinder();
            }
            return null;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
    }
}
