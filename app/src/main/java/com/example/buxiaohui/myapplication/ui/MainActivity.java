package com.example.buxiaohui.myapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.utils.ImageUtils;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.GenericDraweeView;

public class MainActivity extends Activity {
    GenericDraweeView genericDraweeView0;
    GenericDraweeView genericDraweeView1;
    public static String imagePath0 = "http://imgsrc.baidu.com/forum/w%3D580/sign=774fb1f88f1001e94e3c1407880f7b06/cb1349540923dd5482cd5f79d309b3de9c824861.jpg";
    public static String imagePath1 = "http://c.hiphotos.baidu.com/image/pic/item/503d269759ee3d6d27b00fed46166d224f4adea6.jpg";
    public static String imagePath2 = "http://c.hiphotos.baidu.com/image/pic/item/a71ea8d3fd1f413465c48d58201f95cad1c85e21.jpg";
    public static String imagePath3 = "http://c.hiphotos.baidu.com/image/pic/item/9e3df8dcd100baa1cfc1cf9d4210b912c8fc2e22.jpg";
    String imagePathFailure = "http://images.rednet.cn/articleimage/2010/03/05/1713562041111.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initImageUtils();
    }

    private void initImageUtils(){
        genericDraweeView0 = (GenericDraweeView)findViewById(R.id.genericDraweeView0);
        genericDraweeView1 = (GenericDraweeView)findViewById(R.id.genericDraweeView1);
        ImageUtils.setImageUrl(genericDraweeView0,imagePath0,R.drawable.icon_failure,
                R.drawable.icon_placeholder,R.drawable.icon_downloading,R.drawable.icon_retry ,null);
        ImageUtils.setImageUrl(genericDraweeView1,imagePath1,R.drawable.icon_failure,
                R.drawable.icon_placeholder,R.drawable.icon_downloading,R.drawable.icon_retry ,null);
        if(genericDraweeView1.getHierarchy()!=null){
            genericDraweeView1.getHierarchy().setRoundingParams(new RoundingParams()
                    .setRoundAsCircle(true)
                    .setBorder(R.color.colorAccent,10));
        }
        genericDraweeView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });

    }
}
