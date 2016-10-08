package com.example.buxiaohui.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.widget.FrescoSelfDefineView;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.GenericDraweeView;

public class SecondActivity extends Activity {
    FrescoSelfDefineView frescoSelfDefineView0;
    FrescoSelfDefineView frescoSelfDefineView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        init();
    }

    private void initImage() {
        frescoSelfDefineView0 = (FrescoSelfDefineView) findViewById(R.id.testView0);
        frescoSelfDefineView0.showImage();
        frescoSelfDefineView1 = (FrescoSelfDefineView) findViewById(R.id.testView1);
        frescoSelfDefineView1.showImage();
    }

    private void init() {
        initImage();
        ((Button) findViewById(R.id.bt_switch)).setText(frescoSelfDefineView0.getState() ? "Now is Single" : "Now is MultiDraweeHolder");
        findViewById(R.id.bt_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frescoSelfDefineView0.showImage();
                frescoSelfDefineView1.showImage();
                ((Button) v).setText(frescoSelfDefineView0.getState() ? "Now is Single" : "Now is MultiDraweeHolder");
            }
        });
    }
}
