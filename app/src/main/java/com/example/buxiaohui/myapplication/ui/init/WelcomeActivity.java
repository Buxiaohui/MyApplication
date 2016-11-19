package com.example.buxiaohui.myapplication.ui.init;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.buxiaohui.myapplication.Global;
import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.ui.BaseActivity;
import com.example.buxiaohui.myapplication.ui.home.MainActivity;
import com.example.buxiaohui.myapplication.utils.SharePreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buxiaohui on 8/10/2016.
 */
public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager mViewPAger;

    @BindView(R.id.imageView)
    ImageView startImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_spalsh);
        initSpalshPage();
    }

    private void initSpalshPage() {
        if (SharePreferenceUtil.getBoolean(SharePreferenceUtil.S_EVER_IN)) {
            initWelcomePages();
            //TODO
            //显示引导页viewpager
        } else {
            //显示欢迎页2秒后跳转到主页
            initStartPage();
            handler.sendEmptyMessageDelayed(1, 2000);
        }
    }

    private void initWelcomePages() {
        mViewPAger.setVisibility(View.VISIBLE);
        mViewPAger.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                //return false;
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                return super.instantiateItem(container, position);
            }
        });
    }

    private void initStartPage() {
        startImageView.setVisibility(View.VISIBLE);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if(Global.isRmberLogin()){
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                }else{
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }

            }

        }
    };

    private void register() {
        boolean isLogin = true;
        if (isLogin) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
        }
    }
}
