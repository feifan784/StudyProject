package com.xufeifan.application.process;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xufeifan.application.IConnectService;
import com.xufeifan.application.IMessageService;
import com.xufeifan.application.IServiceManager;
import com.xufeifan.application.MessageReceiveListener;
import com.xufeifan.application.R;
import com.xufeifan.application.process.entity.Message;

/**
 * AIDL
 * <p>
 * IPC普通调用会等待方法执行完毕，也就是如果子线程有阻塞也会阻塞主线程，这时候就会用到关键字oneway
 * in inout out 三个关键字的区别：主对子输入；主和子相互输入；子对主输入；
 * <p>
 * Messenger
 * 通过系统Message传递数据，可用Bundle对数据添加，底层也是实现AIDL，通过Hander和Binder完成跨进程，多并非少用
 */
public class RemoteActivity extends AppCompatActivity {

    private TextView tvConnect, tvDisConnect, tvIsConnect, tvSend, tvRegister, tvUnRegister, tvMessenger;
    private IConnectService connectServiceProxy;
    private IMessageService messageServiceProxy;
    private IServiceManager managerProxy;
    private Messenger messengerProxy;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            bundle.setClassLoader(Message.class.getClassLoader());
            final Message message = bundle.getParcelable("messenger_reply");

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RemoteActivity.this, message.getContent(), Toast.LENGTH_SHORT).show();
                }
            }, 3000);

        }
    };
    private MessageReceiveListener messageReceiveListener = new MessageReceiveListener.Stub() {
        @Override
        public void receiveMessage(final Message message) throws RemoteException {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RemoteActivity.this, message.getContent(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
    private Messenger clientMessenger = new Messenger(handler);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        initView();

        initListener();

        initService();
    }

    private void initService() {
        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                try {
                    managerProxy = IServiceManager.Stub.asInterface(iBinder);
                    connectServiceProxy = IConnectService.Stub.asInterface(managerProxy.getService(IConnectService.class.getSimpleName()));
                    messageServiceProxy = IMessageService.Stub.asInterface(managerProxy.getService(IMessageService.class.getSimpleName()));
                    messengerProxy = new Messenger(managerProxy.getService(Messenger.class.getSimpleName()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    private void initListener() {
        tvConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    connectServiceProxy.connect();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        tvDisConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    connectServiceProxy.disConnect();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        tvIsConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    boolean isConn = connectServiceProxy.isConnected();
                    Toast.makeText(RemoteActivity.this, String.valueOf(isConn), Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message();
                message.setContent("Messsage from main");
                try {
                    messageServiceProxy.sendMessage(message);
                    Log.i("xfftll", String.valueOf(message.isSendSuccess()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    messageServiceProxy.registerMessageReceiveListener(messageReceiveListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        tvUnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    messageServiceProxy.unRegisterMessageReceiveListener(messageReceiveListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        tvMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Message message = new Message();
                    message.setContent("send message by Messenger on main");

                    android.os.Message data = new android.os.Message();
                    data.replyTo = clientMessenger;
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("messenger", message);
                    data.setData(bundle);

                    messengerProxy.send(data);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initView() {
        tvConnect = findViewById(R.id.tvConnect);
        tvDisConnect = findViewById(R.id.tvDisConnect);
        tvIsConnect = findViewById(R.id.tvIsConnect);
        tvSend = findViewById(R.id.tvSend);
        tvRegister = findViewById(R.id.tvRegister);
        tvUnRegister = findViewById(R.id.tvUnRegister);
        tvMessenger = findViewById(R.id.tvMessenger);
    }
}
