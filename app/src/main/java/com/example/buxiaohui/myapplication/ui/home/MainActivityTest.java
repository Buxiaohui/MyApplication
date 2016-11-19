package com.example.buxiaohui.myapplication.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.buxiaohui.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityTest extends FragmentActivity implements TabHost.OnTabChangeListener {
    public static String TAG = "MainActivity";
    @BindView(android.R.id.tabcontent)
    FrameLayout contentContainer;
    @BindView(android.R.id.tabhost)
    FragmentTabHost tabHost;
    @BindView(R.id.content_container)
    FrameLayout container;
    private int[] tabTitles = {R.string.tab_left, R.string.tab_right};
    private int[] tabIcons = {R.drawable.icon_bottom_message_tab, R.drawable.icon_bottom_contact_tab};

    public static void open(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, MainActivityTest.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        ButterKnife.bind(this);
        initTabHost();
    }

    private void initTabHost() {
        Class[] fs = {MessageListFragment.class, ContctListFragment.class};
        tabHost.setup(this, getSupportFragmentManager(), R.id.content_container);
        for (int i = 0; i < tabIcons.length; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(getResources().getString(tabTitles[i])).setIndicator(getView(i));
            tabHost.addTab(tabSpec, fs[i], null);
        }
        tabHost.setOnTabChangedListener(this);

    }

    private View getView(int i) {
        //取得布局实例
        View view = View.inflate(this, R.layout.layout_bottom_tab, null);

        //取得布局对象
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.text);

        //设置图标
        imageView.setImageResource(tabIcons[i]);
        //设置标题
        textView.setText(tabTitles[i]);
        return view;
    }

    @Override
    public void onTabChanged(String tabId) {

    }
}
