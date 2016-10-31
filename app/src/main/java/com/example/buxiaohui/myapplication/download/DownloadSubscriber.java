package com.example.buxiaohui.myapplication.download;

import okhttp3.ResponseBody;
import rx.Producer;
import rx.Subscriber;

/**
 * Created by buxiaohui on 17/10/2016.
 */

public class DownloadSubscriber<T extends ResponseBody> extends Subscriber<T> {
    private final String TAG = "DownLoadSubscriber";

    private FileReponseListener fileReponseListener;
    private DownloadItem downloadItem;
    private DownloadTask downloadTask;

    public DownloadSubscriber(FileReponseListener fileReponseListener, DownloadItem downloadInfo, DownloadTask downloadTask) {
        this.fileReponseListener = fileReponseListener;
        this.downloadItem = downloadInfo;
        this.downloadItem.setDownloadSubscriber(this);
        this.downloadTask = downloadTask;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void setProducer(Producer p) {
        super.setProducer(p);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

    }
}
