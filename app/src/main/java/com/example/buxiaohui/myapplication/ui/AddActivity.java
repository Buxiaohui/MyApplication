package com.example.buxiaohui.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.utils.AccountUtils;
import com.example.buxiaohui.myapplication.utils.LogUtils;
import com.example.buxiaohui.myapplication.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bxh on 11/12/16.
 */

public class AddActivity extends AppCompatActivity {
    private final static String TAG = "AddActivity";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search)
    SearchView searchView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static void open(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, AddActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        init();
        AccountUtils.getInstance().searchUsersSync("bxh");
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new AddUserResultListAdapter());
    }

    private void initSearchView() {
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = searchView.getQuery() != null ? searchView.getQuery().toString() : null;
                request(s);
            }
        });
    }

    private void request(String s) {
        LogUtils.D(TAG, "s=" + s);
        if (!TextUtils.isEmpty(s)) {

        } else {
            ToastUtils.show("--empty---");
        }
    }

}
