package com.example.buxiaohui.myapplication.utils;

/**
 * Created by buxiaohui on 31/10/2016.
 */

public class LoginUtils {
    public static boolean isLogin() {
        return SharePreferenceUtil.getBoolean(SharePreferenceUtil.S_IS_LOGIN, false);
    }

    public static boolean isFirstIn() {
        return SharePreferenceUtil.getBoolean(SharePreferenceUtil.S_IS_FIRST_IN, false);
    }

    public static boolean isRemember() {
        return SharePreferenceUtil.getBoolean(SharePreferenceUtil.S_IS_REMBER_LOGIN, false);
    }

    public static boolean isAutoLogin() {
        return SharePreferenceUtil.getBoolean(SharePreferenceUtil.S_IS_REMBER_LOGIN, false);
    }

    public static String getUserName(){
        return SharePreferenceUtil.getString(SharePreferenceUtil.S_USER_NAME, "");
    }

    public static String getPsw(){
        return SharePreferenceUtil.getString(SharePreferenceUtil.S_PASSWORD, "");
    }
}
