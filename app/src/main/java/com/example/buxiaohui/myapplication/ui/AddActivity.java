package com.example.buxiaohui.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.bean.Account;
import com.example.buxiaohui.myapplication.contract.AddUserContract;
import com.example.buxiaohui.myapplication.utils.AccountUtils;
import com.example.buxiaohui.myapplication.utils.LogUtils;
import com.example.buxiaohui.myapplication.utils.ToastUtils;

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
 * Created by bxh on 11/12/16.
 */

public class AddActivity extends AppCompatActivity implements AddUserContract.ViewInterface {
    private final static String TAG = "AddActivity";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search)
    SearchView searchView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    List<Account> list;

    public static void open(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, AddActivity.class));
        }
    }
    public static void open(Context context,int type) {
        if (context != null) {
            Intent intent = new Intent(context, AddActivity.class);
            intent.putExtra("type",type);
            context.startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        init();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void init() {
        initToolbar();
        initListView();
        initSearchView();
    }

    private void initToolbar() {
        //toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initListView() {
        list = new ArrayList<Account>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new AddUserResultListAdapter(list, this));
    }

    private void initSearchView() {
//        searchView.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View viewInterface) {
//                String s = searchView.getQuery() != null ? searchView.getQuery().toString() : null;
//                searchUsersByName(s);
//            }
//        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //test(query);
                searchUsersByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
                ((AddUserResultListAdapter) recyclerView.getAdapter()).refresh(o);
            }
        }.execute(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                ((AddUserResultListAdapter) recyclerView.getAdapter()).refresh(accounts);
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
