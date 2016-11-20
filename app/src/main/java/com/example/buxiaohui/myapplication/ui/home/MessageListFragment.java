package com.example.buxiaohui.myapplication.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.buxiaohui.myapplication.Global;
import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bxh on 11/11/16.
 */

public class MessageListFragment extends Fragment {
    private static final String TAG = "ContctListFragment";

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.D(TAG, "--onReceive action=" + intent.getAction());
        }
    };
    @BindView(R.id.refresh_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(android.R.id.list)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        manager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new MessageListAdapter());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReciver();
    }

    @Override
    public void onPause() {
        super.onPause();
        unRegister();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void registerReciver() {
        LogUtils.D(TAG, "--registerReciver");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Global.REQUEST_ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, intentFilter);
    }

    private void unRegister() {
        LogUtils.D(TAG, "--unRegister");
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }
}
