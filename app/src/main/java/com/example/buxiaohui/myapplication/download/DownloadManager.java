package com.example.buxiaohui.myapplication.download;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buxiaohui on 17/10/2016.
 */

public class DownloadManager {

    private static DownloadManager downloadManager;
    private List<DownloadItem> downloadItemList;

    public DownloadManager() {
        this.downloadItemList = new ArrayList<DownloadItem>();
    }

    public static DownloadManager getInstance() {
        if (downloadManager == null) {
            downloadManager = new DownloadManager();
        }
        return downloadManager;
    }

    public void addTask(DownloadTask downloadTask) {

    }

    public void addTask(DownloadItem downloadItem) {
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.setDownloadItem(downloadItem);
        downloadItemList.add(downloadItem);
    }


    public void restartTask(DownloadItem downloadItem) {
        downloadItem.getDownloadTask().start(true);
    }

    public void startTask(DownloadItem downloadItem) {
        downloadItem.getDownloadTask().start(false);
    }

    public void stopTask(DownloadItem downloadItem) {
        downloadItem.getDownloadTask().stop();
    }
}
