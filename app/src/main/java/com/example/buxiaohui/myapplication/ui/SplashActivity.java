package com.example.buxiaohui.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.buxiaohui.myapplication.utils.LoginUtils;
import com.example.buxiaohui.myapplication.utils.SharePreferenceUtil;

/**
 * Created by buxiaohui on 8/10/2016.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //first in
        if(SharePreferenceUtil.getBoolean(SharePreferenceUtil.S_IS_FIRST_IN,false)){
            startActivity(new Intent(this,WelcomActivity1.class));
        }else{
            startActivity(new Intent(this,LoginActivity.class));
        }
    }

}
