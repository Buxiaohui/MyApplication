package com.example.buxiaohui.myapplication.utils;

import android.text.TextUtils;

/**
 * Created by buxiaohui on 31/10/2016.
 */

public class LoginUtils {
    public static boolean isLogin() {
        return SharePreferenceUtil.getBoolean(SharePreferenceUtil.S_IS_LOGIN, false);
    }

    public static boolean isEverIn() {
        return SharePreferenceUtil.getBoolean(SharePreferenceUtil.S_EVER_IN, false);
    }

    public static void everIn() {
        SharePreferenceUtil.saveBoolean(SharePreferenceUtil.S_EVER_IN, true);
    }

    public static boolean isRemember() {
        return SharePreferenceUtil.getBoolean(SharePreferenceUtil.S_IS_REMBER_LOGIN, false);
    }

    public static boolean isAutoLogin() {
        return SharePreferenceUtil.getBoolean(SharePreferenceUtil.S_IS_AUTO_LOGIN, false);
    }

    public static void setAutoLogin(boolean isEnableAutoLogin) {
        SharePreferenceUtil.saveBoolean(SharePreferenceUtil.S_IS_AUTO_LOGIN, isEnableAutoLogin);
    }

    public static String getUserName() {
        return SharePreferenceUtil.getString(SharePreferenceUtil.S_USER_NAME, "");
    }

    public static String getPsw() {
        return SharePreferenceUtil.getString(SharePreferenceUtil.S_PASSWORD, "");
    }

    public static void saveLogin(String userName, String psw) {
        SharePreferenceUtil.saveString(SharePreferenceUtil.S_USER_NAME, userName);
        SharePreferenceUtil.saveString(SharePreferenceUtil.S_PASSWORD, psw);
    }

    public static boolean isStoredLoginInfoAvailable() {
        return !TextUtils.isEmpty(getUserName()) && !TextUtils.isEmpty(getPsw());
    }

    public static void saveremember(boolean isRemember) {
        SharePreferenceUtil.saveBoolean(SharePreferenceUtil.S_IS_REMBER_LOGIN, isRemember);
    }

    public static boolean isVisitor() {
        return SharePreferenceUtil.getBoolean(SharePreferenceUtil.S_IS_REGISTED_USER, false);
    }

    public static String getUserInfo() {
        //TODO
        return null;
    }


}
