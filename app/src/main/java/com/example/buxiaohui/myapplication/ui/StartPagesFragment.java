package com.example.buxiaohui.myapplication.ui;

import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buxiaohui.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buxiaohui on 15/10/2016.
 */

public class StartPagesFragment extends Fragment {

    @BindView(R.id.viewPager)
    ViewPager mViewPAger;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start_pages, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(view);
        initPages();
    }

    private void initPages() {
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
                View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_start_pages, container, false);
                ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
                TextView textView = (TextView) view.findViewById(R.id.text);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                int[] ids = {R.drawable.icon_test0, R.drawable.icon_test1, R.drawable.icon_test2};
                int[] strs = {R.string.start_page_title0, R.string.start_page_title1, R.string.start_page_title2};
                textView.setText(strs[position]);
                textView.setTag(position);
                imageView.setImageResource(ids[position]);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getTag() != null && (Integer) v.getTag() == 2) {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                    }
                });
                return view;
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
