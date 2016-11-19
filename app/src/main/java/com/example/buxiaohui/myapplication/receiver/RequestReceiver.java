package com.example.buxiaohui.myapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.buxiaohui.myapplication.utils.LogUtils;

/**
 * Created by bxh on 11/19/16.
 */

public class RequestReceiver extends BroadcastReceiver {
    private static final String TAG = "RequestReceiver";
    public RequestReceiver() {
        super();
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.D(TAG,"--onReceive action="+intent.getAction());
    }
}
