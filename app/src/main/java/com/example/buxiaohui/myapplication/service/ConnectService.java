package com.example.buxiaohui.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;

import com.example.buxiaohui.myapplication.utils.AccountUtils;
import com.example.buxiaohui.myapplication.utils.LogUtils;
import com.example.buxiaohui.myapplication.utils.LoginUtils;

/**
 * Created by bxh on 11/17/16.
 */

public class ConnectService extends Service {
    public static final String FULL_PATH = "com.example.buxiaohui.myapplication.service.ConnectService";
    public static final String TAG = "ConnectService";
    private boolean stopKeepConnect;
    private Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //TODO
                    stopKeepConnect = true;
                    break;
                case 2:
                    //TODO
                    stopKeepConnect = false;
                    break;
            }
        }
    });

    public ConnectService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!stopKeepConnect) {
                        LogUtils.D("ConnectService", "当前线程id=" + Thread.currentThread().getId());
                        try {
                            if (!AccountUtils.getInstance().isConnect()) {
                                AccountUtils.getInstance().ensureConnect();
                            }

                            Thread.sleep(9000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
