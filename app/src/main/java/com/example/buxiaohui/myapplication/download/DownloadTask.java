package com.example.buxiaohui.myapplication.download;

import com.example.buxiaohui.myapplication.download.factory.DownLoadFileFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by buxiaohui on 17/10/2016.
 */

public class DownloadTask {
    public static final int STATE_WAITING = 0;
    public static final int STATE_PAUSE = 1;
    public static final int STATE_FINISH = 2;
    public static final int STATE_ERROR = 3;
    public static final int STATE_DOWNLOADING = 4;

    public static final int TYPE_DOWNLOAD = 0;
    public static final int TYPE_UPLOAD = 1;

    private DownloadItem downloadItem;
    private FileReponseListener responseListener;

    private long startProgress;

    public DownloadTask() {
    }

    public DownloadTask(DownloadItem downloadItem, FileReponseListener httpResponseListener) {
        this.downloadItem = downloadItem;
        this.responseListener = httpResponseListener;
    }

    public DownloadItem getDownloadItem() {
        return downloadItem;
    }

    public void setDownloadItem(DownloadItem downloadItem) {
        this.downloadItem = downloadItem;
    }

    public ResponseListener getResponseListener() {
        return responseListener;
    }

    public void setResponseListener(FileReponseListener httpResponseListener) {
        this.responseListener = httpResponseListener;
    }


    public void start(boolean isRestart) {
        if (this.downloadItem.getState() == DownloadTask.STATE_DOWNLOADING) {
            return;
        }
        if (downloadItem.getState() == DownloadTask.STATE_FINISH && !isRestart) {
            return;
        }
        this.isRestart = isRestart;
        if (isRestart) {
            if (downloadItem.getState() == DownloadTask.STATE_FINISH) {
                stop();
            }
            resetProgress();
            startProgress = 0L;
        }
        this.downloadItem.setState(DownloadTask.STATE_DOWNLOADING);
        getClient().downLoadFile(this.downloadItem, this.responseListener, this);

    }

    public void stop() {
        this.downloadItem.setState(DownloadTask.STATE_WAITING);
        if (this.downloadItem != null && this.downloadItem.getDownloadSubscriber() != null) {
            this.downloadItem.getDownloadSubscriber().unsubscribe();
        }
        if (responseListener != null) {
            responseListener.onExecuting(0L, downloadItem.getFileLength(), false);
        }

        if (this.downloadItem.getState() != DownloadTask.STATE_FINISH) {
            resetProgress();
            File file = new File(downloadItem.getFilePath() + downloadItem.getFileName() + fileCacheSuffix);
            if (file.exists()) file.delete();
        }
    }

    public void resume() {
        start(false);
    }

    public void reStart() {
        start(true);
    }

    public boolean isRestart() {
        return isRestart;
    }

    public void setRestart(boolean restart) {
        isRestart = restart;
    }

    public boolean isRestart;
    private static final String fileCacheSuffix = ".cache";

    public void writeCache(InputStream inputStream) {
        String savePath = downloadItem.getFilePath() + downloadItem.getFileName() + fileCacheSuffix;
        File file = new File(savePath);
        if (isRestart) {//如果是重新下载
            if (file.exists()) {
                file.delete();
            }
            isRestart = false;
        }
        FileChannel channelOut = null;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rwd");
            channelOut = randomAccessFile.getChannel();
            MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE
                    , downloadItem.getBreakProgress(), downloadItem.getFileLength() - downloadItem.getBreakProgress());
            byte[] buffer = new byte[1024];
            int len;
            int record = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                mappedBuffer.put(buffer, 0, len);
                record += len;
            }

            try {
                inputStream.close();
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void resetProgress() {
        this.downloadItem.setBreakProgress(0L);
        this.downloadItem.setCurrentProgress(0L);
        this.downloadItem.setFileLength(0L);
    }

    private DownLoadClient getClient() {
        return new DownLoadClient.Builder()
                .addRxJavaCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addDownLoadFileFactory(DownLoadFileFactory.create(this.responseListener, this.downloadItem))
                .build();
    }

}
