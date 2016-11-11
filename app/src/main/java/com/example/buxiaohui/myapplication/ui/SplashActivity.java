package com.example.buxiaohui.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.buxiaohui.myapplication.Config;
import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.utils.LoginUtils;
import com.example.buxiaohui.myapplication.utils.SharePreferenceUtil;

/**
 * Created by buxiaohui on 8/10/2016.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        mDelayHandler.sendEmptyMessageDelayed(1, Config.SPLASH_DELAY_TIME);
    }

    Handler mDelayHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                //first in
                if (!LoginUtils.isEverIn()) {
                    startActivity(new Intent(SplashActivity.this, WelcomActivity1.class));
                    LoginUtils.everIn();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                releaseHandler();
                finish();
            }
        }
    };

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
}
