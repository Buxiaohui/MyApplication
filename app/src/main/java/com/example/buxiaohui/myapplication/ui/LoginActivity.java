package com.example.buxiaohui.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.callback.AccountListener;
import com.example.buxiaohui.myapplication.ui.ChatWindow.MainActivity;
import com.example.buxiaohui.myapplication.utils.AccountUtils;
import com.example.buxiaohui.myapplication.utils.LogUtils;
import com.example.buxiaohui.myapplication.utils.LoginUtils;
import com.example.buxiaohui.myapplication.utils.SharePreferenceUtil;
import com.example.buxiaohui.myapplication.utils.StringUtils;
import com.example.buxiaohui.myapplication.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册账户
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, AccountListener {
    private final static String TAG = "LoginActivity";
    @BindView(R.id.id_auto_checkbox)
    CheckBox mAutoCheckBox;
    @BindView(R.id.id_remember_checkbox)
    CheckBox mRememberCheckBox;
    @BindView(R.id.id_name_input)
    EditText mUserName;
    @BindView(R.id.id_psw_input)
    EditText mPsW;
    @BindView(R.id.id_register)
    TextView mRegister;
    @BindView(R.id.id_forget)
    TextView mForget;
    @BindView(R.id.id_login)
    TextView mLogin;
    @BindView(R.id.id_top_icon)
    ImageView mTopImageView;

    public static void open(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    @Override
    public void accountCallback(int operation, int responseCode) {
        LogUtils.D(TAG, "----operation" + operation + "---:" + responseCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        if (LoginUtils.isRemember() && !TextUtils.isEmpty(LoginUtils.getPsw()) && !TextUtils.isEmpty(LoginUtils.getUserName())) {
            //TODO
            //login

        }
        //just for test
        //test();

    }

    @OnClick(R.id.id_register)
    public void register(View v) {
        ToastUtils.show("--register--");
        RegisterActivity.open(this);
        finish();
    }

    @OnClick(R.id.id_visitor)
    public void asVisitor() {
        MainActivity.open(this);
        finish();
    }

    @OnClick(R.id.id_login)
    public void login() {
//        if (StringUtils.isAvailable(mPsW.getText()) && StringUtils.isAvailable(mUserName.getText())) {
//            AccountUtils.getInstance().loginAsync(mUserName.getText().toString(), mPsW.getText().toString());
//        }
        AccountUtils.getInstance().loginAsync("test01", "123");

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    protected void handleMessage(Message msg) {
        MainActivity.open(this);
        finish();
    }

    private void onLoginComplete(int responseCode) {
        SharePreferenceUtil.saveBoolean(SharePreferenceUtil.S_IS_LOGIN, true);
    }


}
