package com.example.buxiaohui.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.buxiaohui.myapplication.R;

import butterknife.ButterKnife;

/**
 * 注册账户
 * */
public class RegisterActivity extends Activity {
    EditText loginName;
    EditText passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_login);
        init();
    }


    private void init() {
    }
}
