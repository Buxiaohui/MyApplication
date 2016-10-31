package com.example.buxiaohui.myapplication;

import android.app.Application;

//import com.example.buxiaohui.myapplication.database.DaoMaster;
import com.example.buxiaohui.myapplication.database.DbManager;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by buxiaohui on 4/10/2016.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Global.APP_CONTEXT = this;
        Fresco.initialize(this);
//        DbManager.getInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Global.APP_CONTEXT = null;
    }
}
