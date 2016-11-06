package com.example.buxiaohui.myapplication.ui.ChatWindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.buxiaohui.myapplication.R;

public class MainActivityTest extends Activity {
    public static String TAG = "MainActivity";

    public static void open(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, MainActivityTest.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
