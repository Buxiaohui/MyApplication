package com.example.buxiaohui.myapplication.utils;

import android.text.Editable;
import android.text.TextUtils;

/**
 * Created by bxh on 11/6/16.
 */

public class StringUtils {
    public static boolean isAvailable(Editable editable) {
        return (editable != null && !TextUtils.isEmpty(editable.toString()));
    }

    public static String safe(String s) {
        return "" + s;
    }
}
