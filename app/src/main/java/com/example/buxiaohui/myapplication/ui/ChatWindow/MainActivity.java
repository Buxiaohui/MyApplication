package com.example.buxiaohui.myapplication.ui.ChatWindow;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.ui.AddGroupActivity;
import com.example.buxiaohui.myapplication.ui.AddUserActivity;
import com.example.buxiaohui.myapplication.utils.LogUtils;
import com.example.buxiaohui.myapplication.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {
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
    private SearchView searchView;

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
        initToolbar();
        initDrawer();
        initTabHost();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.user_1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
    }

    private void initDrawer() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                LogUtils.D(TAG, "---onDrawerClosed");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                LogUtils.D(TAG, "---onDrawerOpened");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
        if (search != null) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //TODO
                    LogUtils.D(TAG, "onQueryTextSubmit query=" + query);
                    //AccountUtils.getInstance().searchUsersSync(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    LogUtils.D(TAG, "onQueryTextChange query=" + query);
                    //TODO
                    return true;

                }

            });
        }


        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 处理一级菜单
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtils.D(TAG, "item.getItemId()=" + item.getItemId());
        switch (item.getItemId()) {
            case R.id.search:
                //ToastUtils.show("处理一级菜单search..");
                break;
            case R.id.share:
                //ToastUtils.show("处理一级菜单share..");
                break;
            case R.id.more:
                //ToastUtils.show("处理一级菜单more..");
                break;
            case R.id.add:
                ToastUtils.show("处理二级菜单add..");
                AddUserActivity.open(this);
                break;
            case R.id.add_group:
                AddGroupActivity.open(this);
                //ToastUtils.show("处理二级菜单test..");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}