package com.example.buxiaohui.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

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
    @BindView(R.id.id_eye)
    ImageView mEye;

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

        if (!TextUtils.isEmpty(LoginUtils.getPsw()) && !TextUtils.isEmpty(LoginUtils.getUserName())) {
            LogUtils.D(TAG, "---init set name & psw");
            mUserName.setText(LoginUtils.getUserName());
            mPsW.setText(LoginUtils.getPsw());

        }
        //just for test
        //test();
        mRememberCheckBox.setChecked(LoginUtils.isRemember());
        mRememberCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked && StringUtils.isAvailable(mPsW.getText()) && StringUtils.isAvailable(mUserName.getText())) {
//                    LoginUtils.saveLogin(mUserName.getText().toString(), mPsW.getText().toString());
//                    LoginUtils.saveremember(true);
//                } else {
//                    LoginUtils.saveremember(false);
//                    LoginUtils.saveLogin("", "");
//                }
                LoginUtils.saveremember(isChecked);
            }
        });
        mAutoCheckBox.setChecked(LoginUtils.isAutoLogin());
        mAutoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LoginUtils.setAutoLogin(isChecked);
            }
        });

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
        if (StringUtils.isAvailable(mPsW.getText()) && StringUtils.isAvailable(mUserName.getText())) {
            loginAsync(mUserName.getText().toString(), mPsW.getText().toString());
            if (mRememberCheckBox.isChecked()) {
                LogUtils.D(TAG, "---login save name & psw");
                LoginUtils.saveLogin(mUserName.getText().toString(), mPsW.getText().toString());
            } else {
                LoginUtils.saveLogin("", "");
            }
        }
    }

    public void loginAsync(final String userName, final String psw) {
        Observable o = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int result = AccountUtils.getInstance().login(userName, psw);
                subscriber.onNext(result);

            }
        }).subscribeOn(Schedulers.io());

        Subscriber<Integer> s = new Subscriber<Integer>() {
            @Override
            public void onNext(Integer o) {
                LogUtils.D(TAG, "---login onNext result=" + (o != null ? o.intValue() : -1));
                if (o != null && o.intValue() != -1) {
                    MainActivity.open(LoginActivity.this);
                    finish();
                }
            }

            @Override
            public void onCompleted() {
                LogUtils.D(TAG, "-loginAsync--onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.D(TAG, "-loginAsync--onError e:" + e.toString());

            }
        };
        o.subscribe(s);

    }

    @OnClick(R.id.id_eye)
    public void onEyeClick() {
        mEye.setSelected(!mEye.isSelected());
        if (!mEye.isSelected()) {
            mPsW.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            mPsW.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
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
