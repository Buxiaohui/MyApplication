package com.example.buxiaohui.myapplication.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.example.buxiaohui.myapplication.Global;

/**
 * Created by buxiaohui on 31/10/2016.
 */

public class ToastUtils {

    public static void show(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(Global.APP_CONTEXT, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void show(int msgId) {
        if (msgId > 0) {
            Toast.makeText(Global.APP_CONTEXT, msgId, Toast.LENGTH_SHORT).show();
        }
    }
}
