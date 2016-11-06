package com.example.buxiaohui.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.bean.RegisterBean;
import com.example.buxiaohui.myapplication.ui.ChatWindow.MainActivity;
import com.example.buxiaohui.myapplication.utils.AccountUtils;
import com.example.buxiaohui.myapplication.utils.LogUtils;
import com.example.buxiaohui.myapplication.utils.StringUtils;
import com.example.buxiaohui.myapplication.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册账户
 */
public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";
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
        LogUtils.D(TAG,"--register");
        if(StringUtils.isAvailable(mUserName.getText()) && StringUtils.isAvailable(mPassWord.getText())){
            AccountUtils.getInstance().registerAsync(new RegisterBean()
                    .setUserName(mUserName.getText().toString()).setPsw(mPassWord.getText().toString()));
            ToastUtils.show("register new account");
        }
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
