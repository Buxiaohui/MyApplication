package com.example.buxiaohui.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

import com.example.buxiaohui.myapplication.Global;

/**
 * Created by buxiaohui on 12/10/2016.
 */

public class SharePreferenceUtil {
    public final static String S_EVER_IN = "isEverInApp";
    public final static String S_IS_REMBER_LOGIN = "isRember";
    public final static String S_IS_AUTO_LOGIN = "isAutoLogin";
    public final static String S_IS_LOGIN = "isLogin";
    public final static String S_USER_NAME = "userName";
    public final static String S_PASSWORD = "psw";
    public final static String S_IS_REGISTED_USER = "isRegisted";
    private static SharedPreferences s = Global.APP_CONTEXT.getSharedPreferences("TestApp", Context.MODE_PRIVATE);

    public static SharedPreferences getGloableSharedPreferences() {
        if (s == null) {
            return Global.APP_CONTEXT.getSharedPreferences("TestApp", Context.MODE_PRIVATE);
        }
        return s;
    }

    public static void saveString(String key, String value) {
        SharedPreferences.Editor edit = getGloableSharedPreferences().edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defValue) {
        return getGloableSharedPreferences().getString(key, defValue);
    }

    public static void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor edit = getGloableSharedPreferences().edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key,false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getGloableSharedPreferences().getBoolean(key, defaultValue);
    }

}
