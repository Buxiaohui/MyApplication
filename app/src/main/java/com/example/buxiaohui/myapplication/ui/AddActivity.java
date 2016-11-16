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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bxh on 11/12/16.
 */

public abstract class AddActivity<T> extends AppCompatActivity {
    private final static String TAG = "AddUserActivity";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search)
    SearchView searchView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    protected  List<T> list;

    public static void open(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, AddActivity.class));
        }
    }

    public static void open(Context context, int type) {
        if (context != null) {
            Intent intent = new Intent(context, AddActivity.class);
            intent.putExtra("type", type);
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

    protected void init() {
        initToolbar();
        initListView();
        initSearchView();
    }

    protected void initToolbar() {
        //toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initListView() {
        list = new ArrayList<T>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(initAdapter());
    }

    protected void initSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public abstract void search(String s);
    public abstract AddAdapter initAdapter();

}
