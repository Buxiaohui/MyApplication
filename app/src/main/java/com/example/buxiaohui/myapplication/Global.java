package com.example.buxiaohui.myapplication;

import android.content.Context;

import com.example.buxiaohui.myapplication.utils.SharePreferenceUtil;

/**
 * Created by buxiaohui on 4/10/2016.
 */
public class Global {
    public static Context APP_CONTEXT;

    public static boolean isRmberLogin(){
        return SharePreferenceUtil.getBoolean(SharePreferenceUtil.S_IS_REMBER_LOGIN,false);
    }

}
