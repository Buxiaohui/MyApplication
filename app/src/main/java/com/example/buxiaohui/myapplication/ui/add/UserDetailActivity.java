package com.example.buxiaohui.myapplication.ui.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.example.buxiaohui.myapplication.Paramas;
import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.bean.Account;
import com.example.buxiaohui.myapplication.ui.BaseActivity;
import com.example.buxiaohui.myapplication.utils.AccountUtils;
import com.example.buxiaohui.myapplication.utils.LogUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bxh on 11/18/16.
 */

public class UserDetailActivity extends BaseActivity {
    public static String TAG = "UserDetailActivity";
    @BindView(R.id.id_use_info_container)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    List<Object> list;

    public static void open(Context activity, String id) {
        if (activity != null) {
            Intent intent = new Intent();
            intent.setClass(activity, UserDetailActivity.class);
            intent.putExtra(Paramas.USER_NAME, id);
            activity.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        initView();
        initToolbar();
        initData();
    }

    protected void initToolbar() {
        //toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initView() {
        UserDetailAdapter adapter = new UserDetailAdapter();
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        list = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Paramas.USER_NAME) && !TextUtils.isEmpty(intent.getStringExtra(Paramas.USER_NAME))) {
                getFriend(intent.getStringExtra(Paramas.USER_NAME));
                LogUtils.D(TAG, "initData query=" + intent.getStringExtra(Paramas.USER_NAME));
            }
        }
    }

    private void getFriend(String s) {
        Observable.just(s).map(new Func1<String, List<Account>>() {
            @Override
            public List<Account> call(String s) {
                return AccountUtils.getInstance().searchUsers(s);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Account>>() {
            @Override
            public void onCompleted() {

            }


            @Override
            public void onError(Throwable e) {
                LogUtils.D(TAG, "onError e=" + e.toString());
            }

            @Override
            public void onNext(List<Account> accounts) {
                LogUtils.D(TAG, "onNext accounts=" + (accounts == null ? "null" : new Gson().toJson(accounts)));
                ((UserDetailAdapter) recyclerView.getAdapter()).refreshHead(accounts.get(0));
            }
        });

    }
}
