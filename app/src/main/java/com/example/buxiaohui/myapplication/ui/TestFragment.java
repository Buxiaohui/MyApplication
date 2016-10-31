package com.example.buxiaohui.myapplication.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.example.buxiaohui.myapplication.R;

/**
 * Created by buxiaohui on 28/10/2016.
 */

public class TestFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.test);
    }
}

