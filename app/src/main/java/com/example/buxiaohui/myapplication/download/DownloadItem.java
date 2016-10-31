package com.example.buxiaohui.myapplication.download;

/**
 * Created by buxiaohui on 17/10/2016.
 */

public class DownloadItem extends BaseDownloadItem {
    DownloadTask downloadTask;
    DownloadSubscriber downloadSubscriber;

    public DownloadItem() {
        setState(DownloadTask.STATE_WAITING);
        setOperationType(DownloadTask.TYPE_DOWNLOAD);
    }

    public DownloadTask getDownloadTask() {
        return downloadTask;
    }

    public void setDownloadTask(DownloadTask downloadTask) {
        this.downloadTask = downloadTask;
    }

    public DownloadSubscriber getDownloadSubscriber() {
        return downloadSubscriber;
    }

    public void setDownloadSubscriber(DownloadSubscriber downloadSubscriber) {
        this.downloadSubscriber = downloadSubscriber;
    }

}
