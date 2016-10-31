package com.example.buxiaohui.myapplication.utils;

import com.example.buxiaohui.myapplication.download.DownloadItem;
import com.example.buxiaohui.myapplication.download.DownloadTask;
import com.example.buxiaohui.myapplication.download.ResponseListener;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;


/**
 * Created by buxiaohui on 17/10/2016.
 */

public class DownloadUtils {

    public static void download(DownloadItem downloadItem, ResponseListener listener, DownloadTask downloadTask){
        Observable.create(new Observable.OnSubscribe<ResponseBody>() {
            @Override
            public void call(Subscriber<? super ResponseBody> subscriber) {

            }
        });
    }
}
