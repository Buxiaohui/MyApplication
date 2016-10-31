package com.example.buxiaohui.myapplication.download;

import android.net.http.HttpResponseCache;

/**
 * Created by buxiaohui on 17/10/2016.
 */

public interface ResponseListener {
    public void onSucess(String result);

    public void onFailure(String result, Throwable throwable);

    public void onExecuting(long totalLength, long currenLength,boolean isFinish);
}
