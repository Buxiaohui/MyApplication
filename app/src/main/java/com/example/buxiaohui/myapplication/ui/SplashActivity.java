package com.example.buxiaohui.myapplication.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.buxiaohui.myapplication.Config;
import com.example.buxiaohui.myapplication.Global;
import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.service.ConnectService;
import com.example.buxiaohui.myapplication.ui.ChatWindow.MainActivity;
import com.example.buxiaohui.myapplication.utils.AccountUtils;
import com.example.buxiaohui.myapplication.utils.LogUtils;
import com.example.buxiaohui.myapplication.utils.LoginUtils;
import com.example.buxiaohui.myapplication.utils.ToastUtils;

import rx.Subscriber;

/**
 * Created by buxiaohui on 8/10/2016.
 */
public class SplashActivity extends BaseActivity {
    public final static String TAG = "SplashActivity";
    Handler mDelayHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                //first in
                if (!LoginUtils.isEverIn()) {
                    startActivity(new Intent(SplashActivity.this, WelcomActivity1.class));
                    releaseHandler();
                    finish();
                    LoginUtils.everIn();
                } else if (AccountUtils.getInstance().isKeepLoginState()) {
                    LogUtils.D(TAG, "already login");
                    MainActivity.open(SplashActivity.this);
                    releaseHandler();
                    finish();
                } else if (LoginUtils.isAutoLogin() && LoginUtils.isStoredLoginInfoAvailable()) {
                    LogUtils.D(TAG, "auto login");
                    AccountUtils.getInstance().loginAsync(LoginUtils.getUserName(), LoginUtils.getPsw(), new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtils.D(TAG, "onError auto login error,will login again");
                            ToastUtils.show("auto login error! please login again");
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            releaseHandler();
                            finish();
                        }

                        @Override
                        public void onNext(Integer integer) {
                            if (integer != null && integer.intValue() >= 0) {
                                LogUtils.D(TAG, "onNext auto login success");
                                MainActivity.open(SplashActivity.this);
                                releaseHandler();
                                finish();
                            } else {
                                LogUtils.D(TAG, "onNext auto login error,will login again");
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                releaseHandler();
                                finish();
                            }

                        }
                    });

                } else {
                    LogUtils.D(TAG, "need login");
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    releaseHandler();
                    finish();
                }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        mDelayHandler.sendEmptyMessageDelayed(1, Config.SPLASH_DELAY_TIME);
        startService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseHandler();
    }

    private void releaseHandler() {
        if (mDelayHandler != null) {
            mDelayHandler.removeCallbacksAndMessages(null);
        }
    }

    private void startService() {
        //error for sdk > 5.0
        // Service Intent must be explicit:
//        Intent intent = new Intent();
//        intent.setAction("android.bxh.im.action");
//        startService(intent);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(Global.PACKAGE_NAME, ConnectService.FULL_PATH));
        startService(intent);
    }

}
