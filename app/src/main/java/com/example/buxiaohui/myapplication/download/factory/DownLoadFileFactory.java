package com.example.buxiaohui.myapplication.download.factory;

import com.example.buxiaohui.myapplication.download.DownLoadResponseBody;
import com.example.buxiaohui.myapplication.download.DownloadItem;
import com.example.buxiaohui.myapplication.download.FileReponseListener;
import com.example.buxiaohui.myapplication.download.ProgressChangeListener;
import com.example.buxiaohui.myapplication.download.ResponseListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Yuan on 2016/8/26.
 * Detail 下載攔截器
 */
public class DownLoadFileFactory implements Interceptor {

    private FileReponseListener fileReponseListener;
    private DownloadItem downloadItem;

    public DownLoadFileFactory() {
    }

    public DownLoadFileFactory(FileReponseListener fileReponseListener, DownloadItem downloadItem) {
        this.fileReponseListener = fileReponseListener;
        this.downloadItem = downloadItem;
    }

    public void setDownloadItem(DownloadItem downloadItem) {
        this.downloadItem = downloadItem;
    }

    public void setFileReponseListener(FileReponseListener fileReponseListener) {
        this.fileReponseListener = fileReponseListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder().addHeader("RANGE", "bytes=" + downloadItem.getBreakProgress() + "-").build();
        Response originalResponse = chain.proceed(request);
        DownLoadResponseBody body = new DownLoadResponseBody(this.downloadItem, originalResponse.body(), fileReponseListener);
        Response response = originalResponse.newBuilder().body(body).build();
        return response;
    }

    public static DownLoadFileFactory create() {
        return new DownLoadFileFactory();
    }

    public static DownLoadFileFactory create(FileReponseListener fileRespondResult, DownloadItem downloadItem) {
        return new DownLoadFileFactory(fileRespondResult, downloadItem);
    }

}
