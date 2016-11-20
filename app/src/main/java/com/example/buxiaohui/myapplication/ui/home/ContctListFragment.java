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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.buxiaohui.myapplication.Global;
import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.ui.Chat.TalkActivity;
import com.example.buxiaohui.myapplication.utils.AccountUtils;
import com.example.buxiaohui.myapplication.utils.LogUtils;

import org.jivesoftware.smack.roster.RosterEntry;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bxh on 11/11/16.
 */

public class ContctListFragment extends Fragment {
    private static final String TAG = "ContctListFragment";
    @BindView(R.id.refresh_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(android.R.id.list)
    ExpandableListView expandableListView;
    ContactListAdapter adapter;
    List<RosterEntry> friendsSet;


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.D(TAG, "--onReceive action=" + intent.getAction());
        }
    };

    private void init() {
        adapter = new ContactListAdapter();
        adapter.refresh(friendsSet);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                LogUtils.D(TAG, "--onChildClick groupPosition="+groupPosition+"--childPosition="+childPosition);
                //TODO send msg
                TalkActivity.open(getActivity());
                return false;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                LogUtils.D(TAG, "--onGroupClick groupPosition="+groupPosition);

                return false;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO Request
                getAllFriendsList();
            }
        });
        getAllFriendsList();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        init();
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

    private void registerReciver() {
        LogUtils.D(TAG, "--registerReceiver");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Global.REQUEST_ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, intentFilter);
    }

    private void unRegister() {
        LogUtils.D(TAG, "--unRegister");
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }

    @Override
    public void onPause() {
        super.onPause();
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
        unRegister();
    }

    private void getAllFriendsList() {
        LogUtils.D(TAG, "--getAllFriendsList");
                Observable.just("").map(new Func1<String, List<RosterEntry>>() {
            @Override
            public List<RosterEntry> call(String s) {
                return AccountUtils.getInstance().getAllFriendsList();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<RosterEntry>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.D(TAG, "Throwable =" + e.toString());
            }

            @Override
            public void onNext(List<RosterEntry> result) {
                //TODO
                LogUtils.D(TAG, "getAllFriendsList onNext");
                adapter.refresh(result);
                swipeRefreshLayout.setRefreshing(false);
                expandableListView.collapseGroup(0);
                expandableListView.expandGroup(0);

            }
        });
    }
}
