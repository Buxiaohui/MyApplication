package com.example.buxiaohui.myapplication.ui.init;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.ui.init.LoginActivity;

/**
 * Created by buxiaohui on 31/10/2016.
 */

public class WelcomeFragment extends Fragment {
    private int mIndex;


    public WelcomeFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome_item, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int[] mWelcomeImageIds = {R.drawable.icon_test0, R.drawable.icon_test1, R.drawable.icon_test2, R.drawable.icon_test3};
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("index")) {
            this.mIndex = bundle.getInt("index", -1);
        }
        if (mIndex >= 0 && mIndex < mWelcomeImageIds.length) {
            ((ImageView) view.findViewById(R.id.imageView)).setImageResource(mWelcomeImageIds[mIndex]);
        }
        Log.d("test","mIndex:"+mIndex);
        if (mIndex == mWelcomeImageIds.length - 1) {
            ((TextView) view.findViewById(R.id.text)).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.text)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() != null) {
                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                }
            });
        } else {
            ((TextView) view.findViewById(R.id.text)).setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
}
