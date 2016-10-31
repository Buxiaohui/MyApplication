package com.example.buxiaohui.myapplication.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buxiaohui.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 注册账户
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.id_name_input)
    EditText mUserName;
    @BindView(R.id.id_psw_input)
    EditText mPassWord;
    @BindView(R.id.id_email_input)
    EditText mEmail;
    @BindView(R.id.id_register)
    TextView mRegister;
    @BindView(R.id.id_eye)
    ImageView mEye;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    public static void open(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, RegisterActivity.class));
        }
    }

    @OnClick(R.id.id_register)
    public void register() {
        //TODO
    }

    @OnClick(R.id.id_visitor)
    public void asVisitor() {
        MainActivity.open(this);
        finish();
    }

    @OnClick(R.id.id_eye)
    public void onEyeClick() {
        mEye.setSelected(!mEye.isSelected());
        if (!mEye.isSelected()) {
            mPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            mPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

}
