package com.example.buxiaohui.myapplication.ui.Chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ListView;

import com.example.buxiaohui.myapplication.Global;
import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.ui.BaseActivity;
import com.example.buxiaohui.myapplication.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bxh on 11/11/16.
 */

public class TalkActivity extends BaseActivity {
    private static final String TAG = "TalkActivity";

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.D(TAG, "--onReceive action=" + intent.getAction());
        }
    };

    @BindView(R.id.chat_list)
    ListView msgList;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.id_input)
    EditText editText;

    public static void open(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, TalkActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        initToolbar(toolbar);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        unRegister();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void registerReceiver() {
        LogUtils.D(TAG, "--registerReceiver");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Global.REQUEST_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }

    private void unRegister() {
        LogUtils.D(TAG, "--unRegister");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
