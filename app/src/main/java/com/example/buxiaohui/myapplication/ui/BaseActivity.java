package com.example.buxiaohui.myapplication.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by buxiaohui on 8/10/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver mBroadcastReceiver;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onReceive(context, intent);
            }
        };
        IntentFilter filter = new IntentFilter();
        addFilter(filter);
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, filter);
    }

    protected  void addFilter(IntentFilter filter){}

    protected  void onReceive(Context context, Intent intent){}

    protected void test() {
        mHandler.sendEmptyMessageDelayed(1, 2000);
    }

    protected void handleMessage(Message msg) {
    }

    protected void release() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mLocalBroadcastManager != null && mBroadcastReceiver != null) {
            mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    protected void initToolbar(Toolbar toolbar) {
        //toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
