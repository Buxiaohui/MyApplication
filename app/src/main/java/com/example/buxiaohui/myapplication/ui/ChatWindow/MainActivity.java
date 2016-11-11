package com.example.buxiaohui.myapplication.ui.ChatWindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements TabHost.OnTabChangeListener {
    public static String TAG = "MainActivity";
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.left_drawer)
    ListView listView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(android.R.id.tabcontent)
    FrameLayout contentContainer;
    @BindView(android.R.id.tabhost)
    FragmentTabHost tabHost;


    private int[] tabTitles = {R.string.tab_left, R.string.tab_right};
    private int[] tabIcons = {R.drawable.icon_bottom_message_tab, R.drawable.icon_bottom_contact_tab};

    public static void open(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        //startActivity(new Intent(MainActivity.this,MainActivityTest.class));
    }

    private void init() {
        initDrawer();
        initToolbar();
        initTabHost();
    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.user_1);
    }

    private void initDrawer() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        listView.setBackgroundColor(Color.WHITE);
        String[] menus = {"menu0", "menu1", "menu2", "menu3", "menu4", "menu5", "menu6", "menu7"};
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, menus));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO
                ToastUtils.show("menu" + position);
            }
        });
    }

    private void initTabHost() {
        Class[] fs = {MessageListFragment.class, ContctListFragment.class};
        tabHost.setup(this, getSupportFragmentManager(), R.id.content_container);
        for (int i = 0; i < tabIcons.length; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(getResources().getString(tabTitles[i])).setIndicator(getBottomTabView(i));
            tabHost.addTab(tabSpec, fs[i], null);
        }
        tabHost.setOnTabChangedListener(this);

    }

    private View getBottomTabView(int i) {
        //View.inflate
        //取得布局实例
        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_tab, null);

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
