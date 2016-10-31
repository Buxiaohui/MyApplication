package com.example.buxiaohui.myapplication.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.bean.TestBean;
import com.example.buxiaohui.myapplication.bean.User;
import com.example.buxiaohui.myapplication.database.DbManager;
import com.example.buxiaohui.myapplication.utils.ImageFrescoUtils;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.GenericDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends Activity {
    public static String imagePath0 = "http://imgsrc.baidu.com/forum/w%3D580/sign=774fb1f88f1001e94e3c1407880f7b06/cb1349540923dd5482cd5f79d309b3de9c824861.jpg";
    public static String imagePath1 = "http://c.hiphotos.baidu.com/image/pic/item/503d269759ee3d6d27b00fed46166d224f4adea6.jpg";
    public static String imagePath2 = "http://c.hiphotos.baidu.com/image/pic/item/a71ea8d3fd1f413465c48d58201f95cad1c85e21.jpg";
    public static String imagePath3 = "http://c.hiphotos.baidu.com/image/pic/item/9e3df8dcd100baa1cfc1cf9d4210b912c8fc2e22.jpg";
    GenericDraweeView genericDraweeView0;
    GenericDraweeView genericDraweeView1;
    String imagePathFailure = "http://images.rednet.cn/articleimage/2010/03/05/1713562041111.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initImageUtils();
        //insertData();
        //startActivity(new Intent(this,TestActivity.class));
    }

    public static void open(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, MainActivity.class));
        }
    }

    private void initImageUtils() {
        genericDraweeView0 = (GenericDraweeView) findViewById(R.id.genericDraweeView0);
        genericDraweeView1 = (GenericDraweeView) findViewById(R.id.genericDraweeView1);
        ImageFrescoUtils.setImageUrl(genericDraweeView0, imagePath0, R.drawable.icon_failure,
                R.drawable.icon_placeholder, R.drawable.icon_downloading, R.drawable.icon_retry, null);
        ImageFrescoUtils.setImageUrl(genericDraweeView1, imagePath1, R.drawable.icon_failure,
                R.drawable.icon_placeholder, R.drawable.icon_downloading, R.drawable.icon_retry, null);
        if (genericDraweeView1.getHierarchy() != null) {
            genericDraweeView1.getHierarchy().setRoundingParams(new RoundingParams()
                    .setRoundAsCircle(true)
                    .setBorder(R.color.colorAccent, 10));
        }
        genericDraweeView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });

    }

    private void insertData() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                ArrayList<User> userArrayList = new ArrayList<User>();
                for (int i = 0; i < 5; i++) {
                    User user = new User();
                    user.setAge(10 + i);
                    user.setName("index" + i);
                    user.setUserId(i);
                    //DbManager.getDaoSession().getUserDao().insert(user);
                }
                for (int z = 0; z < 5; z++) {
                    TestBean testBean = new TestBean();
                    testBean.setUserId(z);
                    testBean.setName("testBean_index" + z);
                    //DbManager.getDaoSession().getTestBeanDao().insert(testBean);
                }
                TestBean testBean1 = new TestBean();
                testBean1.setUserId(2);
                testBean1.setName("test");
                User user1 = new User();
                user1.setUserId(2);
                user1.setName("username");
                testBean1.setUser(user1);
                //DbManager.getDaoSession().getTestBeanDao().insert(testBean1);

                Log.d("test", "table user:" + new Gson().toJson(DbManager.getDaoSession().getUserDao().loadAll()));
                Log.d("test", "table testBean:" + new Gson().toJson(DbManager.getDaoSession().getTestBeanDao().loadAll()));
//                DbManager.getDaoSession().getTestBeanDao().insertInTx(testBeanArrayList);
//                DbManager.getDaoSession().getUserDao().deleteAll();
                TestBean testBeanForTest = DbManager.getDaoSession().getTestBeanDao().loadAll().get(2);
                User userForTest = testBeanForTest.getUser();
                Log.d("test", "userForTest:" + new Gson().toJson(userForTest));
                Log.d("test", "testBeanForTest:" + new Gson().toJson(testBeanForTest));

                return null;
            }
        }.execute();

    }
}
