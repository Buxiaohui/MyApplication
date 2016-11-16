package com.example.buxiaohui.myapplication.ui;

import android.content.Context;
import android.content.Intent;

import com.example.buxiaohui.myapplication.bean.Account;
import com.example.buxiaohui.myapplication.contract.AddUserContract;
import com.example.buxiaohui.myapplication.utils.AccountUtils;
import com.example.buxiaohui.myapplication.utils.LogUtils;
import com.example.buxiaohui.myapplication.utils.ToastUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bxh on 11/12/16.
 */

public class AddUserActivity extends AddActivity<Account> implements AddUserContract.ViewInterface {
    private final static String TAG = "AddUserActivity";
    AddUserListAdapter adapter;
    public static void open(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, AddUserActivity.class));
        }
    }

    public static void open(Context context, int type) {
        if (context != null) {
            Intent intent = new Intent(context, AddUserActivity.class);
            intent.putExtra("type", type);
            context.startActivity(intent);
        }
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void search(String s) {
        searchUsersByName(s);
    }

    @Override
    public AddAdapter initAdapter() {
        adapter =  new AddUserListAdapter(list);
        adapter.setView(this);
        return adapter;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    private void searchUsersByName(String s) {
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

            }

            @Override
            public void onNext(List<Account> accounts) {
                ((AddUserListAdapter) recyclerView.getAdapter()).refresh(accounts);
                searchView.clearFocus();
            }
        });

    }

    @Override
    public void addUser(Account account) {
        addFriend(account);
    }

    @Override
    public void addUsers(List<Account> accountList) {
        //TODO

    }

    private void addFriend(Account account) {
        Observable.just(account).map(new Func1<Account, Boolean>() {
            @Override
            public Boolean call(Account s) {

                return AccountUtils.getInstance().addFriend(s.getUserName(), s.getUserName(), null);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.D(TAG, "Throwable =" + e.toString());
            }

            @Override
            public void onNext(Boolean result) {
                //TODO
                ToastUtils.show("add friend success " + String.valueOf(result));
            }
        });
    }
}
