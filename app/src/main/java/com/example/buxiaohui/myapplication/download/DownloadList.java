package com.example.buxiaohui.myapplication.download;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buxiaohui on 18/10/2016.
 */

public class DownloadList {
    private List<DownloadItem> downloadItemList = new ArrayList<DownloadItem>();

    public void addDownloadItem(DownloadItem downloadItem) {
        this.downloadItemList.add(downloadItem);
    }

    public List<DownloadItem> getDownloadItemList() {
        return downloadItemList;
    }

    public void setDownloadItemList(List<DownloadItem> downloadItemList) {
        this.downloadItemList = downloadItemList;
    }


}
