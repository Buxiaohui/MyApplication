package com.example.buxiaohui.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.buxiaohui.myapplication.Global;
import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.bean.Account;
import com.example.buxiaohui.myapplication.contract.AddGroupContract;
import com.example.buxiaohui.myapplication.utils.AccountUtils;
import com.example.buxiaohui.myapplication.utils.LogUtils;
import com.example.buxiaohui.myapplication.utils.ToastUtils;

import org.jivesoftware.smack.roster.RosterGroup;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bxh on 11/12/16.
 */

public class AddGroupActivity extends AddActivity<RosterGroup> implements AddGroupContract.View {
    private final static String TAG = "AddGroupActivity";

    public static void open(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, AddGroupActivity.class));
        }
    }

    @Override
    public void search(String s) {
        LogUtils.D(TAG, "search query=" + s);
        searchGroups(s);
    }

    @Override
    public AddAdapter initAdapter() {
        AddGroupListAdapter addGroupListAdapter = new AddGroupListAdapter(list);
        addGroupListAdapter.setView(this);
        return addGroupListAdapter;
    }

    @Override
    protected void initSearchView() {
        searchView.setQueryHint(Global.APP_CONTEXT.getString(R.string.hint_search_groups));
        super.initSearchView();
    }

    private void test(String s) {
        new AsyncTask<String, Void, List<Account>>() {
            @Override
            protected List<Account> doInBackground(String... params) {
                return AccountUtils.getInstance().searchUsers(params[0]);
            }

            @Override
            protected void onPostExecute(List<Account> o) {
                super.onPostExecute(o);
                ((AddUserListAdapter) recyclerView.getAdapter()).refresh(o);
            }
        }.execute(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void searchGroups(String s) {
        Observable.just(s).map(new Func1<String, List<RosterGroup>>() {
            @Override
            public List<RosterGroup> call(String s) {
                return AccountUtils.getInstance().searchGroups(s);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<RosterGroup>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<RosterGroup> accounts) {
                if (accounts == null || accounts.size() <= 0) {
                    ToastUtils.show("no result");
                }
                ((AddGroupListAdapter) recyclerView.getAdapter()).refresh(accounts);
                searchView.clearFocus();
            }
        });

    }

    @Override
    public void addGroup(RosterGroup account) {
        //TODO
    }

    @Override
    public void addGroups(List<RosterGroup> accountList) {
        //TODO
    }

}
