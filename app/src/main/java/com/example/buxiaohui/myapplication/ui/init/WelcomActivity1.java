package com.example.buxiaohui.myapplication.ui.init;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.buxiaohui.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buxiaohui on 8/10/2016.
 */
public class WelcomActivity1 extends FragmentActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mFragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new MyAdapter());
    }

    public class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new WelcomeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", position);
            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
