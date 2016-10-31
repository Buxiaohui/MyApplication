package com.example.buxiaohui.myapplication.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.utils.LoginUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册账户
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.id_auto_checkbox)
    private CheckBox mAutoCheckBox;
    @BindView(R.id.id_remember_checkbox)
    private CheckBox mRememberCheckBox;
    @BindView(R.id.id_name_input)
    EditText mUserName;
    @BindView(R.id.id_psw_input)
    EditText mPsW;
    @BindView(R.id.id_register)
    EditText mRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_login);
        init();
    }


    private void init() {
        if (LoginUtils.isRemember() && !TextUtils.isEmpty(LoginUtils.getPsw()) && !TextUtils.isEmpty(LoginUtils.getUserName())) {
            //TODO
            //login
        }

    }

    @OnClick(R.id.id_register)
    public void register(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.id_retry)
    public void retry(View v) {
        //TODO
    }

    @OnClick(R.id.id_sign_out)
    public void signout(View v) {
        //TODO
    }

    @Override
    public void onClick(View v) {
        
    }
}
