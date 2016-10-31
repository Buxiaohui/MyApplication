package com.example.buxiaohui.myapplication.download;

/**
 * Created by buxiaohui on 18/10/2016.
 */

public interface ProgressChangeListener {
    void onProgressChange(long totalLength, long currentLength, boolean isFinish);
}
